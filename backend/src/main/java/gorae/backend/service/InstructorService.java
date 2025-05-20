package gorae.backend.service;

import gorae.backend.dto.course.AvailabilityAddRequestDto;
import gorae.backend.dto.instructor.AvailabilityDto;
import gorae.backend.entity.instructor.Instructor;
import gorae.backend.entity.instructor.InstructorAvailability;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import gorae.backend.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InstructorService {
    private final InstructorRepository instructorRepository;

    @Transactional
    public void addAvailability(String userId, AvailabilityAddRequestDto dto) {
        int startHour = dto.startHour();
        int endHour = dto.endHour();

        if (startHour == endHour) {
            throw new CustomException(ErrorStatus.CANNOT_BE_START_HOUR_AND_END_HOUR_SAME);
        }

        if (startHour > endHour) {
            throw new CustomException(ErrorStatus.WRONG_HOUR);
        }

        Long instructorId = Long.valueOf(userId);
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));

        InstructorAvailability availability = InstructorAvailability.builder()
                .startTime(LocalTime.of(startHour, 0))
                .endTime(LocalTime.of(endHour, 0))
                .dayOfWeek(dto.dayOfWeek())
                .isActive(true)
                .build();

        if (instructor.hasTimeConflict(availability)) {
            throw new CustomException(ErrorStatus.AVAILABILITY_OVERLAPPED);
        }

        instructor.addAvailability(availability);
        instructorRepository.save(instructor);
    }

    public List<AvailabilityDto> getAvailabilities(String userId) {
        Long instructorId = Long.valueOf(userId);
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));

        return instructor.getAvailabilities().stream()
                .map(InstructorAvailability::toDto)
                .toList();
    }
}
