package gorae.backend.service;

import gorae.backend.common.ProfileUtils;
import gorae.backend.constant.EnrollmentStatus;
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
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final TicketRepository ticketRepository;
    private final CourseRepository courseRepository;
    private final ProfileUtils profileUtils;

    @Transactional
    public EnrollmentDto enroll(String userId, EnrollRequestDto dto) {
        Long studentId = Long.valueOf(userId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));

        Course course = courseRepository.findByPublicId(dto.courseId())
                .orElseThrow(() -> new CustomException(ErrorStatus.COURSE_NOT_FOUND));

        UUID studentPublicId = student.getPublicId();
        UUID coursePublicId = course.getPublicId();
        log.info("[Service] Enroll started for member: {}, course: {}", studentPublicId, coursePublicId);

        if (course.getStartTime().isBefore(Instant.now())) {
            throw new CustomException(ErrorStatus.COURSE_ALREADY_STARTED);
        }

        Ticket ticket = ticketRepository.findFirst()
                .orElseThrow(() -> new CustomException(ErrorStatus.TICKET_NOT_FOUND));

        Enrollment enrollment = Enrollment.addEnrollment(student, course, ticket);
        enrollmentRepository.save(enrollment);

        ticket.useTicket();
        ticketRepository.save(ticket);

        EnrollmentDto enrollmentDto = enrollment.toDto();
        log.info("[Service] Enroll succeeded for member: {}, ticket: {}, enrollment: {}",
                studentPublicId, ticket.getPublicId(), enrollment.getPublicId());
        return enrollmentDto;
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
    public void drop(String userId, UUID enrollmentId) {
        Long studentId = Long.valueOf(userId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));
        Enrollment enrollment = enrollmentRepository.findByPublicId(enrollmentId)
                .orElseThrow(() -> new CustomException(ErrorStatus.ENROLLMENT_NOT_FOUND));

        UUID studentPublicId = student.getPublicId();
        UUID enrollmentPublicId = enrollment.getPublicId();
        log.info("[Service] Drop started for member: {}, enrollment: {}", studentPublicId, enrollmentPublicId);

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
        if (profileUtils.isProdMode() && hoursDifference < 6) {
            throw new CustomException(ErrorStatus.CANNOT_DROP_COURSE_NEAR_START_TIME);
        }

        enrollment.cancelEnrollment();
        enrollmentRepository.save(enrollment);

        Ticket ticket = enrollment.getTicket();
        ticket.returnTicket();
        ticketRepository.save(ticket);
        log.info("[Service] Drop succeeded for member: {}, enrollment: {}", studentPublicId, enrollmentPublicId);
    }
}
