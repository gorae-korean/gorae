package gorae.backend.service;

import gorae.backend.constant.EnrollmentStatus;
import gorae.backend.constant.TicketStatus;
import gorae.backend.dto.enrollment.EnrollRequestDto;
import gorae.backend.dto.enrollment.EnrollmentDto;
import gorae.backend.entity.Course;
import gorae.backend.entity.Enrollment;
import gorae.backend.entity.Student;
import gorae.backend.entity.Ticket;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import gorae.backend.repository.CourseRepository;
import gorae.backend.repository.EnrollmentRepository;
import gorae.backend.repository.StudentRepository;
import gorae.backend.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final TicketRepository ticketRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public EnrollmentDto enroll(String userId, EnrollRequestDto dto) {
        Course course = courseRepository.findById(dto.courseId())
                .orElseThrow(() -> new CustomException(ErrorStatus.COURSE_NOT_FOUND));
        if (course.getStartTime().isBefore(Instant.now())) {
            throw new CustomException(ErrorStatus.COURSE_ALREADY_STARTED);
        }

        Ticket ticket = ticketRepository.findById(dto.ticketId())
                .orElseThrow(() -> new CustomException(ErrorStatus.TICKET_NOT_FOUND));
        if (ticket.getStatus() == TicketStatus.USED) {
            throw new CustomException(ErrorStatus.TICKET_ALREADY_USED);
        }

        Long studentId = Long.valueOf(userId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));
        if (!student.getTickets().contains(ticket)) {
            throw new CustomException(ErrorStatus.TICKET_NOT_FOUND);
        }

        Enrollment enrollment = Enrollment.addEnrollment(student, course);
        enrollmentRepository.save(enrollment);

        ticket.useTicket();
        ticketRepository.save(ticket);

        return enrollment.toDto();
    }

    @Transactional(readOnly = true)
    public List<EnrollmentDto> getEnrollments(String userId) {
        Long studentId = Long.valueOf(userId);
        if (!studentRepository.existsById(studentId)) {
            throw new CustomException(ErrorStatus.MEMBER_NOT_FOUND);
        }
        return enrollmentRepository.findByStudent_Id(studentId)
                .stream()
                .map(Enrollment::toDto)
                .toList();
    }

    @Transactional
    public void drop(String userId, Long enrollmentId) {
        Long studentId = Long.valueOf(userId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new CustomException(ErrorStatus.ENROLLMENT_NOT_FOUND));
        if (!enrollment.getStudent().equals(student)) {
            throw new CustomException(ErrorStatus.NO_PERMISSIONS);
        }
        if (enrollment.getStatus() != EnrollmentStatus.ENROLLED) {
            throw new CustomException(ErrorStatus.INVALID_DROP);
        }

        Instant now = Instant.now();
        Instant courseStartTime = enrollment.getCourse().getStartTime();
        if (courseStartTime.isBefore(now)) {
            throw new CustomException(ErrorStatus.COURSE_ALREADY_STARTED);
        }
        long hoursDifference = Duration.between(now, courseStartTime).toHours();
        if (hoursDifference < 6) {
            throw new CustomException(ErrorStatus.CANNOT_DROP_COURSE_NEAR_START_TIME);
        }

        enrollment.cancelEnrollment();
        enrollmentRepository.save(enrollment);

        Ticket ticket = enrollment.getTicket();
        ticket.returnTicket();
        ticketRepository.save(ticket);
    }
}
