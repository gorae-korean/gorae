package gorae.backend.entity.textbook;

import gorae.backend.common.UrlUtils;
import gorae.backend.constant.textbook.TextbookCategory;
import gorae.backend.constant.textbook.TextbookLevel;
import gorae.backend.dto.textbook.TextbookArticleDto;
import gorae.backend.dto.textbook.TextbookSearchDto;
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
    @Column(nullable = false, columnDefinition = "TEXT")
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

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private int readTime;

    @OneToMany(mappedBy = "textbook", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TextbookCultureTip> cultureTips = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "textbook_id")
    private List<TextbookLessonQuestion> lessonQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "textbook", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public boolean isNew() {
        return Duration.between(getCreatedAt(), Instant.now()).toDays() <= 14;
    }

    public TextbookSearchDto toDto(boolean isKorean) {
        return TextbookSearchDto.builder()
                .id(getPublicId())
                .title(isKorean ? koreanTitle : englishTitle)
                .subtitle(isKorean ? koreanSubtitle : englishSubtitle)
                .thumbnailUrl(UrlUtils.getTextbookThumbnailUrl(getPublicId()))
                .isNew(isNew())
                .readTime(readTime)
                .tags(tags)
                .level(level)
                .build();
    }

    public TextbookArticleDto toArticleDto() {
        return TextbookArticleDto.builder()
                .title(koreanTitle)
                .subtitle(koreanSubtitle)
                .article(article)
                .tags(tags)
                .level(level)
                .readTime(readTime)
                .build();
    }
}
