package gorae.backend.service;

import gorae.backend.common.ProfileUtils;
import gorae.backend.common.TimeUtils;
import gorae.backend.common.google.GoogleHttpClient;
import gorae.backend.dto.client.google.SpaceDto;
import gorae.backend.dto.lecture.LectureDto;
import gorae.backend.entity.Course;
import gorae.backend.entity.Lecture;
import gorae.backend.entity.Member;
import gorae.backend.entity.Student;
import gorae.backend.entity.instructor.Instructor;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import gorae.backend.repository.CourseRepository;
import gorae.backend.repository.InstructorRepository;
import gorae.backend.repository.LectureRepository;
import gorae.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
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
    private final MemberRepository memberRepository;

    private static final int MAX_TIME_LIMIT = 5;

    @Transactional
    public LectureDto createLecture(String userId) throws Exception {
        Long instructorId = Long.valueOf(userId);
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));

        Instant nextOnTime = TimeUtils.getNextHour();
        Course course = courseRepository.findByInstructor_IdAndStartTime(instructorId, nextOnTime)
                .orElseThrow(() -> new CustomException(ErrorStatus.COURSE_NOT_FOUND));

        UUID instructorPublicId = instructor.getPublicId();
        UUID coursePublicId = course.getPublicId();
        log.info("[Service] CreateLecture started for member: {}, course: {}", instructorPublicId, coursePublicId);

        if (lectureRepository.existsByCourse(course)) {
            throw new CustomException(ErrorStatus.LECTURE_ALREADY_EXISTS);
        }

        if (profileUtils.isProdMode() && Duration.between(Instant.now(), nextOnTime).toMinutes() > MAX_TIME_LIMIT) {
            throw new CustomException(ErrorStatus.LECTURE_CAN_BE_CREATED_AS_EARLY_AS_5_MINUTES);
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
    public LectureDto joinLecture(String userId) {
        Long memberId = Long.valueOf(userId);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));

        UUID memberPublicId = member.getPublicId();
        log.info("[Service] JoinLecture started for member: {}", memberPublicId);

        Instant nextOnTime = TimeUtils.getNextHour();
        if (profileUtils.isProdMode() && Duration.between(Instant.now(), nextOnTime).toMinutes() > MAX_TIME_LIMIT) {
            throw new CustomException(ErrorStatus.LECTURE_CAN_BE_JOINED_AS_EARLY_AS_5_MINUTES);
        }

        Lecture lecture;
        LectureDto lectureDto;
        switch (member) {
            case Instructor instructor -> {
                lecture = lectureRepository.findByInstructorAndScheduledStartTime(instructor, nextOnTime)
                        .orElseThrow(() -> new CustomException(ErrorStatus.LECTURE_NOT_FOUND));
                lecture.start();
                lectureRepository.save(lecture);
                lectureDto = lecture.toDto(true);
            }

            case Student student -> {
                lecture = lectureRepository.findByStudentsContainsAndScheduledStartTime(student, nextOnTime)
                        .orElseThrow(() -> new CustomException(ErrorStatus.LECTURE_NOT_FOUND));
                lectureDto = lecture.toDto(false);
            }

            default -> throw new IllegalStateException("잘못된 멤버 인스턴스입니다.");
        }

        log.info("[Service] JoinLecture succeeded for member: {}, lecture: {}", memberPublicId, lecture.getPublicId());
        return lectureDto;
    }
}
