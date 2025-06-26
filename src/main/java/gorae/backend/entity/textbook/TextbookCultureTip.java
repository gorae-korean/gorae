package gorae.backend.entity.textbook;

import gorae.backend.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "culture_tip")
public class TextbookCultureTip extends BaseEntity {
    @NotBlank
    @Column(nullable = false)
    private String title;

    private String subtitle;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @NotBlank
    @URL(message = "이미지 URL이 유효하지 않습니다.")
    @Column(nullable = false)
    private String imageUrl;
}
