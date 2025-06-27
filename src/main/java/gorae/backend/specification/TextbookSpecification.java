package gorae.backend.specification;

import gorae.backend.constant.textbook.TextbookCategory;
import gorae.backend.constant.textbook.TextbookLevel;
import gorae.backend.dto.textbook.TextbookSearchRequestDto;
import gorae.backend.entity.textbook.Textbook;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class TextbookSpecification {
    private TextbookSpecification() {
        throw new IllegalStateException("Specification class");
    }

    public static Specification<Textbook> searchByCondition(TextbookSearchRequestDto dto) {
        return ((root, query, criteriaBuilder) -> {
            Objects.requireNonNull(query).distinct(true);
            return Specification.where(withWord(dto.title()))
                    .and(withTags(dto.category()))
                    .and(withLevel(dto.level()))
                    .toPredicate(root, query, criteriaBuilder);
        });
    }

    private static Specification<Textbook> withWord(String title) {
        return ((root, query, criteriaBuilder) -> {
            if (title == null || title.trim().isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("englishTitle")),
                    "%" + title.toLowerCase() + "%");
        });
    }

    private static Specification<Textbook> withTags(TextbookCategory category) {
        return ((root, query, criteriaBuilder) -> {
            if (category == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("category"), category);
        });
    }

    private static Specification<Textbook> withLevel(TextbookLevel level) {
        return ((root, query, criteriaBuilder) -> {
            if (level == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("level"), level);
        });
    }
}
