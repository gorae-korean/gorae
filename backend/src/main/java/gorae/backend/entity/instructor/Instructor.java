package gorae.backend.entity.instructor;

import gorae.backend.entity.Course;
import gorae.backend.entity.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

import static gorae.backend.common.TimeUtils.timeOverlaps;

@Slf4j
@Entity
@Getter
@DiscriminatorValue("INSTRUCTOR")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Instructor extends Member {
    @OneToMany(mappedBy = "instructor", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Course> courses = new HashSet<>();

    @OneToMany(mappedBy = "instructor", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<InstructorAvailability> availabilities = new HashSet<>();

    @OneToMany(mappedBy = "instructor", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<InstructorUnavailableDate> unavailableDates = new HashSet<>();

    public void addAvailability(InstructorAvailability availability) {
        availabilities.add(availability);
        availability.setInstructor(this);
    }

    public void addUnavailableDate(InstructorUnavailableDate unavailableDate) {
        unavailableDates.add(unavailableDate);
        unavailableDate.setInstructor(this);
    }

    public boolean hasTimeConflict(InstructorAvailability newAvailability) {
        return availabilities.stream()
                .filter(availability -> availability.getDayOfWeek() == newAvailability.getDayOfWeek()
                        && availability.isActive())
                .anyMatch(existing ->
                        timeOverlaps(existing.getStartTime(), existing.getEndTime(),
                                newAvailability.getStartTime(), newAvailability.getEndTime()));
    }
}
