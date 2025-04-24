package gorae.backend.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("INSTRUCTOR")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Instructor extends Member {
}
