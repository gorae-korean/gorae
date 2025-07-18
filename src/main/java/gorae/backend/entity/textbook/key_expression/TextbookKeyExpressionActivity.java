package gorae.backend.entity.textbook.key_expression;

import gorae.backend.dto.textbook.TextbookKeyExpressionActivityDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "key_expression_activity")
public class TextbookKeyExpressionActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(
            name = "key_expression_activity_example",
            joinColumns = @JoinColumn(name = "activity_id")
    )
    private List<ImageAndText> examples = new ArrayList<>();

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    public TextbookKeyExpressionActivityDto toDto() {
        return TextbookKeyExpressionActivityDto.builder()
                .question(question)
                .examples(examples)
                .build();
    }
}
