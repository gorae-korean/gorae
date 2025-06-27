package gorae.backend.entity.textbook;

import gorae.backend.dto.textbook.TextbookVocabularyDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "vocabulary")
public class TextbookVocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String englishExampleSentence1;

    @NotBlank
    @Column(nullable = false)
    private String englishExampleSentence2;

    @NotBlank
    @Column(nullable = false)
    private String koreanExampleSentence1;

    @NotBlank
    @Column(nullable = false)
    private String koreanExampleSentence2;

    @NotBlank
    @Column(nullable = false)
    private String englishVocabulary;

    @NotBlank
    @Column(nullable = false)
    private String koreanVocabulary;

    @NotBlank
    @Column(nullable = false)
    private String pronunciation;

    public TextbookVocabularyDto toDto(int sequence) {
        return TextbookVocabularyDto.builder()
                .sequence(sequence)
                .koreanVocabulary(koreanVocabulary)
                .pronunciation(pronunciation)
                .englishVocabulary(englishVocabulary)
                .koreanExampleSentence1(koreanExampleSentence1)
                .englishExampleSentence1(englishExampleSentence1)
                .koreanExampleSentence2(koreanExampleSentence2)
                .englishExampleSentence2(englishExampleSentence2)
                .build();
    }
}
