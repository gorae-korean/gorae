package gorae.backend.entity.instructor;

import gorae.backend.constant.MemberRole;
import gorae.backend.dto.instructor.InstructorDto;
import gorae.backend.entity.Course;
import gorae.backend.entity.Lecture;
import gorae.backend.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;

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
    @URL
    @Schema(description = "강사 프로필 사진 URL", example = "https://example.com/static/profile.png")
    String profileImageUrl;

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

    public InstructorDto toDto() {
        return new InstructorDto(getName(), profileImageUrl);
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
