package gorae.backend.service;

import gorae.backend.common.TimeUtils;
import gorae.backend.common.google.GoogleHttpClient;
import gorae.backend.constant.TextbookLevel;
import gorae.backend.dto.client.google.SpaceDto;
import gorae.backend.entity.Course;
import gorae.backend.entity.Lecture;
import gorae.backend.entity.Textbook;
import gorae.backend.entity.instructor.Instructor;
import gorae.backend.entity.instructor.InstructorAvailability;
import gorae.backend.entity.instructor.InstructorUnavailableDate;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import gorae.backend.repository.CourseRepository;
import gorae.backend.repository.InstructorRepository;
import gorae.backend.repository.LectureRepository;
import gorae.backend.repository.TextbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScheduledTaskService {
    private final GoogleHttpClient googleHttpClient;
    private final CourseRepository courseRepository;
    private final LectureRepository lectureRepository;
    private final InstructorRepository instructorRepository;
    private final TextbookRepository textbookRepository;

    private static final ZoneId SEOUL_ZONE_ID = ZoneId.of("Asia/Seoul");

    @Scheduled(cron = "0 55 * * * *")
    @Transactional
    public void createLecture() {
        log.info("[System] CreateLecture started");
        Instant onTime = TimeUtils.getNextHour();
        List<Lecture> lectures = courseRepository.findByStartTime(onTime)
                .stream().map(this::getLectureFromCourse)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        lectureRepository.saveAll(lectures);
        log.info("[System] CreateLecture ended");
    }

    private Optional<Lecture> getLectureFromCourse(Course course) {
        try {
            if (lectureRepository.existsByCourse(course)) {
                throw new CustomException(ErrorStatus.LECTURE_ALREADY_EXISTS);
            }
            Instructor instructor = course.getInstructor();
            SpaceDto spaceDto = googleHttpClient.createSpace(instructor);
            return Optional.of(Lecture.schedule(spaceDto.meetingCode(), spaceDto.meetingUri(), course));
        } catch (Exception e) {
            log.error("Error creating lecture for course: {}", course.getId(), e);
            return Optional.empty();
        }
    }

    // TODO: 시간표 갱신 메소드 개선 필요
    @Scheduled(cron = "0 0 * * * Sun")
    @Transactional
    public void updateTimeTable() {
        log.info("[System] UpdateTimeTable started");
        List<Instructor> instructors = instructorRepository.findAll();
        List<Course> newCourses = new ArrayList<>();

        // Java.time 패키지는 일주일의 시작을 월요일, 끝을 일요일로 정의함
        LocalDate nextMonday = LocalDate.now().with(DayOfWeek.MONDAY).plusWeeks(1);
        try {
            instructors.forEach(
                    instructor -> {
                        Set<InstructorAvailability> availabilities = instructor.getAvailabilities()
                                .stream().filter(InstructorAvailability::isActive).collect(Collectors.toSet());
                        Set<InstructorUnavailableDate> unavailableDates = instructor.getUnavailableDates();

                        availabilities.forEach(availability ->
                        {
                            DayOfWeek dayOfWeek = availability.getDayOfWeek();
                            LocalTime startTime = availability.getStartTime();
                            LocalTime endTime = availability.getEndTime();
                            LocalDate courseDate = nextMonday.with(dayOfWeek);

                            ZonedDateTime startDateTime = ZonedDateTime.of(courseDate, startTime, SEOUL_ZONE_ID);
                            ZonedDateTime endDateTime = ZonedDateTime.of(courseDate, endTime, SEOUL_ZONE_ID);
                            Instant courseStartTime = startDateTime.toInstant();
                            Instant courseEndTime = endDateTime.toInstant();

                            boolean isUnavailable = isTimeUnavailable(courseStartTime, courseEndTime, unavailableDates);
                            if (!isUnavailable) {
                                for (ZonedDateTime currentTime = startDateTime;
                                     currentTime.isBefore(endDateTime);
                                     currentTime = currentTime.plusHours(1)) {
                                    boolean hasExistingCourse = courseRepository.existsByInstructorAndStartTime(
                                            instructor, currentTime.toInstant());

                                    if (!hasExistingCourse) {
                                        Textbook textbook = textbookRepository.findFirstByLevel(TextbookLevel.BEGINNER);
                                        Course course = Course.builder()
                                                .title("자동 생성된 강좌")
                                                .startTime(currentTime.toInstant())
                                                .endTime(currentTime.plusHours(1).toInstant())
                                                .instructor(instructor)
                                                .textbook(textbook)
                                                .build();

                                        newCourses.add(course);
                                    }
                                }
                            }
                        });
                    }
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (newCourses.isEmpty()) {
            log.info("[System] No new courses generated for next week");
        } else {
            courseRepository.saveAll(newCourses);
            log.info("[System] Generated {} new courses for next week", newCourses.size());
        }
        log.info("[System] UpdateTimeTable ended");
    }

    private boolean isTimeUnavailable(Instant startTime, Instant endTime, Set<InstructorUnavailableDate> unavailableDates) {
        // TODO: 함수 구현 필요
        return false;
    }
}
