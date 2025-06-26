package gorae.backend.entity.textbook;

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
}
