package gorae.backend.entity.textbook.key_expression;

import gorae.backend.common.UrlUtils;
import gorae.backend.dto.textbook.TextbookKeyExpressionActivityDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "key_expression_activity")
public class TextbookKeyExpressionActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "key_expression_id")
    private TextbookKeyExpression keyExpression;

    @ElementCollection
    @CollectionTable(
            name = "key_expression_activity_example",
            joinColumns = @JoinColumn(name = "activity_id")
    )
    private List<FileNameAndText> examples = new ArrayList<>();

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    public TextbookKeyExpressionActivityDto toDto() {
        UUID textbookId = getKeyExpression().getTextbook().getPublicId();

        List<ImageAndText> convertedExamples = examples.stream()
                .map(example -> {
                    String imageUrl = UrlUtils.getExpressionImageUrl(textbookId, example.getFileName());
                    return new ImageAndText(imageUrl, example.getText());
                }).toList();

        return TextbookKeyExpressionActivityDto.builder()
                .question(question)
                .examples(convertedExamples)
                .build();
    }
}
