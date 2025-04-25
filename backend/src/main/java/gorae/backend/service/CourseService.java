package gorae.backend.service;

import gorae.backend.entity.Course;
import gorae.backend.entity.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public List<Course> searchCourses() {
        return courseRepository.findAll();
    }
}
