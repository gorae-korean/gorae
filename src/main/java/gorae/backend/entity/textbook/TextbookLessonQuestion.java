package gorae.backend.entity.textbook;

import gorae.backend.dto.textbook.TextbookLessonQuestionDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "lesson_question")
public class TextbookLessonQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String koreanQuestion;

    @NotBlank
    @Column(nullable = false)
    private String englishQuestion;

    public TextbookLessonQuestionDto toDto(int sequence) {
        return TextbookLessonQuestionDto.builder()
                .sequence(sequence)
                .koreanQuestion(koreanQuestion)
                .englishQuestion(englishQuestion)
                .build();
    }
}
