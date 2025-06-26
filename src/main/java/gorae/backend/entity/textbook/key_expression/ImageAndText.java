package gorae.backend.entity.textbook.key_expression;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
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
public class ImageAndText {
    @NotBlank
    @URL
    @Column(nullable = false)
    private String imageUrl;

    @NotBlank
    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageAndText that = (ImageAndText) o;
        return Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageUrl, text);
    }
}
