package gorae.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("STUDENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Student extends Member {
    @Column
    private boolean isFirst;
}
