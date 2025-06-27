package gorae.backend.entity.textbook.play_a_role;

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
public class Character {
    @URL
    private String imageUrl;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String englishRole;

    @Column(nullable = false)
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
