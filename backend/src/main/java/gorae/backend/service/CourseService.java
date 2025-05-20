package gorae.backend.service;

import gorae.backend.entity.Course;
import gorae.backend.dto.course.CourseDto;
import gorae.backend.repository.CourseRepository;
import gorae.backend.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    public List<CourseDto> searchCourses(Long textbookId, LocalDateTime startTime) {
        if (textbookId != null && textbookId <= 0) {
            throw new IllegalArgumentException("교재의 ID 값은 양수여야 합니다.");
        }

        List<Course> courses;
        if (textbookId != null && startTime != null) {
            courses = courseRepository.findByTextbook_IdAndStartTime(textbookId, startTime);
        } else if (textbookId != null) {
            courses = courseRepository.findByTextbook_Id(textbookId);
        } else {
            courses = courseRepository.findByStartTime(startTime);
        }

        return courses.stream().map(Course::toDto).toList();
    }
}
