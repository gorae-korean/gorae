package gorae.backend.entity.textbook.key_expression;

import gorae.backend.entity.BaseEntity;
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
public class TextbookKeyExpressionActivity extends BaseEntity {
    @ElementCollection
    @CollectionTable(
            name = "key_expression_activity_example",
            joinColumns = @JoinColumn(name = "activity_id")
    )
    private List<ImageAndText> examples = new ArrayList<>();

    @NotBlank
    @Column(nullable = false)
    private String question;
}
