package gorae.backend.entity.textbook;

import gorae.backend.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "lesson_question")
public class TextbookLessonQuestion extends BaseEntity {
    @NotBlank
    @Column(nullable = false)
    private String koreanQuestion;

    @NotBlank
    @Column(nullable = false)
    private String englishQuestion;
}
