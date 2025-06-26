package gorae.backend.entity.textbook.play_a_role;

import gorae.backend.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "play_a_role_script")
public class TextbookPlayARoleScript extends BaseEntity {
    @NotBlank
    @Column(nullable = false)
    private String koreanLine;

    @NotBlank
    @Column(nullable = false)
    private String englishLine;

    @Embedded
    private Character speaker;
}
