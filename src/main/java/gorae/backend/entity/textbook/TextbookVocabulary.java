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
@Table(name = "vocabulary")
public class TextbookVocabulary extends BaseEntity {
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
}
