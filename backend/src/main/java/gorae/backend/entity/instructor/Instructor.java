package gorae.backend.entity.instructor;

import gorae.backend.constant.MemberRole;
import gorae.backend.entity.Course;
import gorae.backend.entity.Lecture;
import gorae.backend.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static gorae.backend.common.TimeUtils.timeOverlaps;

@Slf4j
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Instructor extends Member {
    @OneToMany(mappedBy = "instructor", orphanRemoval = true, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Course> courses = new HashSet<>();

    @OneToMany(mappedBy = "instructor", orphanRemoval = true, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<InstructorAvailability> availabilities = new HashSet<>();

    @OneToMany(mappedBy = "instructor", orphanRemoval = true, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<InstructorUnavailableDate> unavailableDates = new HashSet<>();

    @OneToMany(mappedBy = "instructor", orphanRemoval = true)
    private List<Lecture> lectures;

    @PrePersist
    public void prePersist() {
        super.setRole(MemberRole.INSTRUCTOR);
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
