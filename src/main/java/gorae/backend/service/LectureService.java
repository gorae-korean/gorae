package gorae.backend.service;

import gorae.backend.common.ProfileUtils;
import gorae.backend.common.google.GoogleHttpClient;
import gorae.backend.constant.CourseStatus;
import gorae.backend.dto.client.google.SpaceDto;
import gorae.backend.dto.lecture.CreateLectureRequestDto;
import gorae.backend.dto.lecture.LectureDto;
import gorae.backend.entity.Course;
import gorae.backend.entity.Lecture;
import gorae.backend.entity.Student;
import gorae.backend.entity.instructor.Instructor;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import gorae.backend.repository.CourseRepository;
import gorae.backend.repository.InstructorRepository;
import gorae.backend.repository.LectureRepository;
import gorae.backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureService {
    private final GoogleHttpClient googleHttpClient;
    private final ProfileUtils profileUtils;
    private final CourseRepository courseRepository;
    private final LectureRepository lectureRepository;
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;

    private static final int MAX_TIME_LIMIT = 5;

    @Transactional
    public LectureDto createLecture(String userId, CreateLectureRequestDto requestDto) throws Exception {
        long instructorId = Long.parseLong(userId);
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));

        Course course = courseRepository.findByPublicIdWithLock(requestDto.courseId())
                .orElseThrow(() -> new CustomException(ErrorStatus.COURSE_NOT_FOUND));

        UUID instructorPublicId = instructor.getPublicId();
        UUID coursePublicId = course.getPublicId();
        log.info("[Service] CreateLecture started for member: {}, course: {}", instructorPublicId, coursePublicId);

        if (!course.getInstructor().equals(instructor)) {
            throw new CustomException(ErrorStatus.NO_PERMISSIONS);
        }

        if (course.getStatus() != CourseStatus.UPCOMING) {
            throw new CustomException(ErrorStatus.INVALID_PARAMETER);
        }

        if (profileUtils.isProdMode() && course.getCurrentCount() <= course.getMaxCount() / 2) {
            throw new CustomException(ErrorStatus.NOT_ENOUGH_STUDENTS);
        }

        if (profileUtils.isProdMode() && Duration.between(Instant.now(), course.getStartTime()).toMinutes() > MAX_TIME_LIMIT) {
            throw new CustomException(ErrorStatus.LECTURE_CAN_BE_CREATED_AS_EARLY_AS_5_MINUTES);
        }

        Optional<Lecture> optionalExistingLecture = lectureRepository.findByCourse(course);
        if (optionalExistingLecture.isPresent()) {
            Lecture existingLecture = optionalExistingLecture.get();
            log.info("[Service] Lecture already created and returned again for member: {}, course: {}, lecture: {}",
                    instructorPublicId, coursePublicId, existingLecture.getPublicId());

            existingLecture.start();
            lectureRepository.save(existingLecture);
            return existingLecture.toDto(true);
        }

        SpaceDto spaceDto = googleHttpClient.createSpace(instructor);
        Lecture lecture = Lecture.schedule(spaceDto.meetingCode(), spaceDto.meetingUri(), course);
        lecture.start();
        lectureRepository.save(lecture);
        log.info("[Service] CreateLecture succeeded for member: {}, course: {}, lecture: {}",
                instructorPublicId, coursePublicId, lecture.getPublicId());
        return lecture.toDto(true);
    }

    @Transactional
    public LectureDto joinLecture(String userId, UUID courseId) {
        long studentId = Long.parseLong(userId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));

        Course course = courseRepository.findByPublicIdWithLock(courseId)
                .orElseThrow(() -> new CustomException(ErrorStatus.COURSE_NOT_FOUND));

        UUID memberPublicId = student.getPublicId();
        UUID coursePublicId = course.getPublicId();
        log.info("[Service] JoinLecture started for member: {}, course: {}", memberPublicId, coursePublicId);

        if (course.getStatus() != CourseStatus.UPCOMING) {
            throw new CustomException(ErrorStatus.INVALID_PARAMETER);
        }

        Instant startTime = course.getStartTime();
        if (profileUtils.isProdMode() && Duration.between(Instant.now(), startTime).toMinutes() > MAX_TIME_LIMIT) {
            throw new CustomException(ErrorStatus.LECTURE_CAN_BE_JOINED_AS_EARLY_AS_5_MINUTES);
        }

        Lecture lecture = lectureRepository.findByStudentsContainsAndCourse(student, course)
                .orElseThrow(() -> new CustomException(ErrorStatus.LECTURE_NOT_FOUND));
        LectureDto lectureDto = lecture.toDto(false);

        log.info("[Service] JoinLecture succeeded for member: {}, course: {}, lecture: {}",
                memberPublicId, coursePublicId, lecture.getPublicId());
        return lectureDto;
    }
}
