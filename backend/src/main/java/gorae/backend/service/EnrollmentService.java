package gorae.backend.service;

import gorae.backend.entity.Course;
import gorae.backend.entity.Enrollment;
import gorae.backend.entity.Student;
import gorae.backend.entity.Ticket;
import gorae.backend.entity.constant.TicketStatus;
import gorae.backend.entity.dto.enrollment.EnrollRequestDto;
import gorae.backend.entity.dto.enrollment.EnrollmentDto;
import gorae.backend.entity.repository.CourseRepository;
import gorae.backend.entity.repository.EnrollmentRepository;
import gorae.backend.entity.repository.StudentRepository;
import gorae.backend.entity.repository.TicketRepository;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final TicketRepository ticketRepository;
    private final CourseRepository courseRepository;

    public EnrollmentDto enroll(String userId, EnrollRequestDto dto) {
        Long studentId = Long.valueOf(userId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));
        Ticket ticket = ticketRepository.findById(dto.ticketId())
                .orElseThrow(() -> new CustomException(ErrorStatus.TICKET_NOT_FOUND));
        if (ticket.getStatus() == TicketStatus.USED) {
            throw new CustomException(ErrorStatus.TICKET_NOT_FOUND);
        }
        if (!student.getTickets().contains(ticket)) {
            throw new CustomException(ErrorStatus.TICKET_NOT_FOUND);
        }

        Course course = courseRepository.findById(dto.courseId())
                .orElseThrow(() -> new CustomException(ErrorStatus.COURSE_NOT_FOUND));
        Enrollment enrollment = Enrollment.createEnrollment(student, course);
        enrollmentRepository.save(enrollment);

        ticket.use();
        ticketRepository.save(ticket);

        return enrollment.toDto();
    }

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
}
