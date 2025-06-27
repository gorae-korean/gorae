package gorae.backend.entity.textbook.key_expression;

import gorae.backend.dto.textbook.TextbookKeyExpressionDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "key_expression")
public class TextbookKeyExpression {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @ElementCollection
    @CollectionTable(
            name = "key_expression_example",
            joinColumns = @JoinColumn(name = "key_expression_id")
    )
    @Column(name = "example")
    private List<String> examples = new ArrayList<>();

    @NotBlank
    @Column(nullable = false)
    private String koreanKeyExpression;

    @NotBlank
    @Column(nullable = false)
    private String englishKeyExpression;

    @NotNull
    @Column(nullable = false)
    private boolean isPublished;

    @NotEmpty
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "key_expression_id")
    private List<TextbookKeyExpressionActivity> keyExpressionActivities = new ArrayList<>();

    public TextbookKeyExpressionDto toDto() {
        return TextbookKeyExpressionDto.builder()
                .koreanExpression(koreanKeyExpression)
                .englishExpression(englishKeyExpression)
                .examples(examples)
                .isPublished(isPublished)
                .activities(keyExpressionActivities.stream().map(TextbookKeyExpressionActivity::toDto).toList())
                .build();
    }
}
