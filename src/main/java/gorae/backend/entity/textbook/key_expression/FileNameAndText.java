package gorae.backend.entity.textbook.key_expression;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(name = "사진 URL 및 텍스트")
public class FileNameAndText {
    @NotBlank
    @Column(nullable = false)
    @Schema(description = "파일 이름", example = "file")
    private String fileName;

    @NotBlank
    @Column(nullable = false)
    @Schema(description = "예시 단어", example = "영화관")
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileNameAndText that = (FileNameAndText) o;
        return Objects.equals(fileName, that.fileName) &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, text);
    }
}
