package gorae.backend.service;

import gorae.backend.dto.course.CourseDto;
import gorae.backend.entity.Course;
import gorae.backend.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public List<CourseDto> searchCourses(UUID textbookId, ZonedDateTime startTime) {
        List<Course> courses;
        if (textbookId != null && startTime != null) {
            courses = courseRepository.findByTextbook_PublicIdAndStartTime(textbookId, startTime.toInstant());
        } else if (textbookId != null) {
            courses = courseRepository.findByTextbook_PublicId(textbookId);
        } else {
            courses = courseRepository.findByStartTime(startTime.toInstant());
        }

        return courses.stream().map(Course::toDto).toList();
    }

    public List<OffsetTime> getTimetable(ZonedDateTime dateTime) {
        Instant startTime = dateTime.toInstant();
        Instant endTime = startTime.plus(23, ChronoUnit.HOURS);
        return courseRepository.findTimeTable(startTime, endTime)
                .stream().filter(course -> course.getCurrentCount() < course.getMaxCount())
                .map(course -> OffsetTime.ofInstant(course.getStartTime(), dateTime.getZone()))
                .distinct()
                .toList();
    }
}
