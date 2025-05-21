package gorae.backend.entity.instructor;

import gorae.backend.dto.instructor.AvailabilityDto;
import gorae.backend.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Table(name = "instructor_availability")
public class InstructorAvailability extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    private boolean isActive;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof InstructorAvailability that)) return false;
        return Objects.equals(instructor, that.instructor) &&
                dayOfWeek == that.dayOfWeek &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instructor, dayOfWeek, startTime, endTime);
    }

    public AvailabilityDto toDto() {
        return AvailabilityDto.builder()
                .startTime(startTime)
                .endTime(endTime)
                .dayOfWeek(dayOfWeek)
                .isActive(isActive)
                .build();
    }
}
