package gorae.backend.entity.instructor;

import gorae.backend.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "instructor_unavailable_date")
public class InstructorUnavailableDate extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    private LocalDate startDate;

    private LocalDate endDate;

    private String reason;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstructorUnavailableDate that)) return false;
        return Objects.equals(instructor, that.instructor) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instructor, startDate, endDate);
    }
}
