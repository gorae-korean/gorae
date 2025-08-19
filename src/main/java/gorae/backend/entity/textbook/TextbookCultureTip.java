package gorae.backend.entity.textbook;

import gorae.backend.common.UrlUtils;
import gorae.backend.dto.textbook.TextbookCultureTipDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "culture_tip")
public class TextbookCultureTip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "textbook_id")
    private Textbook textbook;

    @NotBlank
    @Column(nullable = false)
    private String title;

    private String subtitle;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    private String fileName;

    public TextbookCultureTipDto toDto(int sequence) {
        String imageUrl = UrlUtils.getCultureTipImageUrl(getTextbook().getPublicId(), fileName);

        return TextbookCultureTipDto.builder()
                .sequence(sequence)
                .title(title)
                .subtitle(subtitle)
                .imageUrl(imageUrl)
                .text(text)
                .build();
    }
}
