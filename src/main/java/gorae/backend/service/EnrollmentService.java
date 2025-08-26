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
import java.util.Optional;
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

    private static final int DEADLINE_HOUR = 1;

    @Transactional
    public EnrollmentDto enroll(String userId, EnrollRequestDto dto) {
        long studentId = Long.parseLong(userId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));

        Course course = courseRepository.findByPublicIdWithLock(dto.courseId())
                .orElseThrow(() -> new CustomException(ErrorStatus.COURSE_NOT_FOUND));

        UUID studentPublicId = student.getPublicId();
        UUID coursePublicId = course.getPublicId();
        log.info("[Service] Enroll started for member: {}, course: {}", studentPublicId, coursePublicId);

        Instant startTime = course.getStartTime();
        Instant now = Instant.now();
        if (startTime.isBefore(now)) {
            throw new CustomException(ErrorStatus.COURSE_ALREADY_STARTED);
        }

        long hoursDifference = Duration.between(now, startTime).toHours();
        if (profileUtils.isProdMode() && hoursDifference < DEADLINE_HOUR) {
            throw new CustomException(ErrorStatus.CANNOT_ENROLL_COURSE_NEAR_START_TIME);
        }

        if (enrollmentRepository.existsByStudentAndCourse_StartTimeAndStatus(student, startTime, EnrollmentStatus.ENROLLED)) {
            throw new CustomException(ErrorStatus.CANNOT_ENROLL_MORE);
        }

        if (course.getCurrentCount() >= course.getMaxCount()) {
            throw new CustomException(ErrorStatus.COURSE_IS_FULL);
        }

        Ticket ticket = ticketRepository.findFirstByStudentAndLessonRatio(student, startTime, course.getMaxCount())
                    .orElseThrow(() -> new CustomException(ErrorStatus.TICKET_NOT_FOUND));

        Optional<Enrollment> existingEnrollment = enrollmentRepository.findByStudentAndCourse(student, course);
        Enrollment enrollment;
        if (existingEnrollment.isPresent()) {
            enrollment = existingEnrollment.get();
            enrollment.reEnroll(ticket);
        } else {
            enrollment = Enrollment.addEnrollment(student, course, ticket);
        }

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
        long studentId = Long.parseLong(userId);
        return enrollmentRepository.findByStudent_Id(studentId)
                .stream()
                .map(Enrollment::toDto)
                .toList();
    }

    @Transactional
    public void drop(String userId, UUID enrollmentId) {
        long studentId = Long.parseLong(userId);
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
        if (profileUtils.isProdMode() && hoursDifference < DEADLINE_HOUR) {
            throw new CustomException(ErrorStatus.CANNOT_DROP_COURSE_NEAR_START_TIME);
        }

        Ticket ticket = enrollment.getTicket();
        ticket.returnTicket();
        enrollment.cancelEnrollment();

        enrollmentRepository.save(enrollment);
        ticketRepository.save(ticket);
        log.info("[Service] Drop succeeded for member: {}, enrollment: {}", studentPublicId, enrollmentPublicId);
    }
}
