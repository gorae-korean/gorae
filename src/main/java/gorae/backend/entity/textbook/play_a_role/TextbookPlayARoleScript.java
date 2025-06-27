package gorae.backend.entity.textbook.play_a_role;

import gorae.backend.dto.textbook.TextbookPlayARoleScriptDto;
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
    @Column(nullable = false, length = 1024)
    private String koreanLine;

    @NotBlank
    @Column(nullable = false, length = 1024)
    private String englishLine;

    @Embedded
    private Character speaker;

    public TextbookPlayARoleScriptDto toDto(int sequence) {
        return TextbookPlayARoleScriptDto.builder()
                .sequence(sequence)
                .englishLine(englishLine)
                .koreanLine(koreanLine)
                .speaker(speaker)
                .build();
    }
}
