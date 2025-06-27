package gorae.backend.entity.textbook.play_a_role;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "play_a_role_script")
public class TextbookPlayARoleScript {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String koreanLine;

    @NotBlank
    @Column(nullable = false)
    private String englishLine;

    @Embedded
    private Character speaker;
}
