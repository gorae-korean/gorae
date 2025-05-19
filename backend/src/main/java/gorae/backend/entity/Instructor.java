package gorae.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@DiscriminatorValue("INSTRUCTOR")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Instructor extends Member {
    @OneToMany(mappedBy = "instructor", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Course> courses = new HashSet<>();
}
