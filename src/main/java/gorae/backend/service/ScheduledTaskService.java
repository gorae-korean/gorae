package gorae.backend.service;

import gorae.backend.common.TimeUtils;
import gorae.backend.constant.textbook.TextbookLevel;
import gorae.backend.entity.Course;
import gorae.backend.entity.textbook.Textbook;
import gorae.backend.entity.instructor.Instructor;
import gorae.backend.entity.instructor.InstructorAvailability;
import gorae.backend.entity.instructor.InstructorUnavailableDate;
import gorae.backend.repository.CourseRepository;
import gorae.backend.repository.InstructorRepository;
import gorae.backend.repository.TextbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScheduledTaskService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final TextbookRepository textbookRepository;

    private static final ZoneId SEOUL_ZONE_ID = ZoneId.of("Asia/Seoul");
    private static final ZoneId INDONESIA_ZONE_ID = ZoneId.of("Asia/Jakarta");

    private static final LocalTime INDONESIA_EVENING_START = LocalTime.of(19, 0);
    private static final LocalTime INDONESIA_EVENING_END = LocalTime.of(22, 0);

    @Scheduled(cron = "0 0 * * * Sun")
    @Transactional
    public void updateTimeTable() {
        log.info("[System] UpdateTimeTable started");
        List<Instructor> instructors = instructorRepository.findAll();
        List<Course> newCourses = new ArrayList<>();

        // Java.time 패키지는 일주일의 시작을 월요일, 끝을 일요일로 정의함
        LocalDate nextMonday = LocalDate.now().with(DayOfWeek.MONDAY).plusWeeks(1);
        try {
            instructors.forEach(instructor ->
                    processInstructorAvailability(instructor, nextMonday, newCourses));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        saveNewCoursesIfExist(newCourses);
        log.info("[System] UpdateTimeTable ended");
    }

    private void processInstructorAvailability(Instructor instructor, LocalDate nextMonday, List<Course> newCourses) {
        Set<InstructorAvailability> availabilities = instructor.getAvailabilities()
                .stream()
                .filter(InstructorAvailability::isActive)
                .collect(Collectors.toSet());
        Set<InstructorUnavailableDate> unavailableDates = instructor.getUnavailableDates();

        availabilities.forEach(availability ->
                processAvailabilitySlot(instructor, availability, nextMonday, unavailableDates, newCourses)
        );
    }

    private void processAvailabilitySlot(
            Instructor instructor,
            InstructorAvailability availability,
            LocalDate nextMonday,
            Set<InstructorUnavailableDate> unavailableDates,
            List<Course> newCourses) {

        DayOfWeek dayOfWeek = availability.getDayOfWeek();
        LocalTime startTime = availability.getStartTime();
        LocalTime endTime = availability.getEndTime();
        LocalDate courseDate = nextMonday.with(dayOfWeek);

        ZonedDateTime startDateTime = ZonedDateTime.of(courseDate, startTime, SEOUL_ZONE_ID);
        ZonedDateTime endDateTime = ZonedDateTime.of(courseDate, endTime, SEOUL_ZONE_ID);

        if (!isTimeUnavailable(courseDate, unavailableDates)) {
            createCoursesForTimeSlot(instructor, startDateTime, endDateTime, newCourses);
        }
    }

    private void createCoursesForTimeSlot(
            Instructor instructor,
            ZonedDateTime startDateTime,
            ZonedDateTime endDateTime,
            List<Course> newCourses) {

        for (ZonedDateTime currentTime = startDateTime;
             currentTime.isBefore(endDateTime);
             currentTime = currentTime.plusHours(1)) {

            ZonedDateTime indonesiaCurrentTime = currentTime.withZoneSameInstant(INDONESIA_ZONE_ID);
            LocalTime indonesiaLocalTime = indonesiaCurrentTime.toLocalTime();

            boolean isIndonesiaEveningTime = !indonesiaLocalTime.isBefore(INDONESIA_EVENING_START) &&
                    indonesiaLocalTime.isBefore(INDONESIA_EVENING_END);

            boolean hasExistingCourse = courseRepository.existsByInstructorAndStartTime(
                    instructor, currentTime.toInstant());

            if (isIndonesiaEveningTime && !hasExistingCourse) {
                Course course = createNewCourse(instructor, currentTime);
                newCourses.add(course);
            }
        }
    }

    private Course createNewCourse(Instructor instructor, ZonedDateTime startTime) {
        // TODO: 교재 선택 알고리즘 수정 필요
        Textbook textbook = textbookRepository.findFirstByLevel(TextbookLevel.BASIC);
        return Course.builder()
                .startTime(startTime.toInstant())
                .endTime(startTime.plusHours(1).toInstant())
                .instructor(instructor)
                .textbook(textbook)
                .build();
    }

    private void saveNewCoursesIfExist(List<Course> newCourses) {
        if (newCourses.isEmpty()) {
            log.info("[System] No new courses generated for next week");
        } else {
            courseRepository.saveAll(newCourses);
            log.info("[System] Generated {} new courses for next week", newCourses.size());
        }
    }

    private boolean isTimeUnavailable(LocalDate courseDate, Set<InstructorUnavailableDate> unavailableDates) {
        return unavailableDates.stream()
                .anyMatch(unavailableDate -> TimeUtils.isDateBetween(courseDate,
                        unavailableDate.getStartDate(), unavailableDate.getEndDate()));
    }
}