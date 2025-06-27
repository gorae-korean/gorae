package gorae.backend.entity.textbook;

import gorae.backend.dto.textbook.TextbookCultureTipDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "culture_tip")
public class TextbookCultureTip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    private String subtitle;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @URL(message = "이미지 URL이 유효하지 않습니다.")
    @Column(length = 2048)
    private String imageUrl;

    public TextbookCultureTipDto toDto(int sequence) {
        return TextbookCultureTipDto.builder()
                .sequence(sequence)
                .title(title)
                .subtitle(subtitle)
                .imageUrl(imageUrl)
                .text(text)
                .build();
    }
}
