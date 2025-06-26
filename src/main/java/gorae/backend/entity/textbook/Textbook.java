package gorae.backend.entity.textbook;

import gorae.backend.constant.textbook.TextbookCategory;
import gorae.backend.constant.textbook.TextbookLevel;
import gorae.backend.dto.textbook.TextbookDto;
import gorae.backend.entity.BaseEntity;
import gorae.backend.entity.textbook.key_expression.TextbookKeyExpression;
import gorae.backend.entity.textbook.play_a_role.TextbookPlayARoleScene;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Textbook extends BaseEntity {
    @NotBlank
    @Column(nullable = false)
    private String article;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private TextbookCategory category;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private TextbookLevel level;

    @NotBlank
    @Column(nullable = false)
    private String englishTitle;

    @NotBlank
    @Column(nullable = false)
    private String koreanTitle;

    private String englishSubtitle;

    private String koreanSubtitle;

    @NotBlank
    @URL(message = "교재 대표 이미지 URL이 유효하지 않습니다.")
    @Column(nullable = false)
    private String thumbnailUrl;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private int readTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "textbook_id")
    private List<TextbookCultureTip> cultureTips = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "textbook_id")
    private List<TextbookLessonQuestion> lessonQuestions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "textbook_id")
    private List<TextbookKeyExpression> keyExpressions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "textbook_id")
    private List<TextbookLetsTalkAbout> letsTalkAbouts = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "textbook_id")
    private List<TextbookPlayARoleScene> playARoles = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "textbook_id")
    private List<TextbookVocabulary> vocabularies = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "textbook_tag",
            joinColumns = @JoinColumn(name = "textbook_id")
    )
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();

    public TextbookDto toDto() {
        return TextbookDto.builder()
                .id(this.getPublicId())
                .tags(tags)
                .level(level)
                .build();
    }

    public boolean isNew() {
        return Duration.between(getCreatedAt(), Instant.now()).toDays() <= 14;
    }
}
