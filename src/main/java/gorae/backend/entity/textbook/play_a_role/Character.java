package gorae.backend.entity.textbook.play_a_role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "예시 회화 캐릭터")
public class Character {
    @URL
    @Schema(description = "캐릭터 사진 URL", example = "https://example.com/static/image.png")
    private String imageUrl;

    @Column(nullable = false)
    @Schema(description = "캐릭터 이름", example = "리나")
    private String name;

    @Column(nullable = false)
    @Schema(description = "영어 역할", example = "International Student")
    private String englishRole;

    @Column(nullable = false)
    @Schema(description = "한국어 역할", example = "외국인 유학생")
    private String koreanRole;

    @Override
    public int hashCode() {
        return Objects.hash(imageUrl, name, englishRole, koreanRole);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Character that = (Character) obj;
        return Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(name, that.name) &&
                Objects.equals(englishRole, that.englishRole) &&
                Objects.equals(koreanRole, that.koreanRole);
    }
}
