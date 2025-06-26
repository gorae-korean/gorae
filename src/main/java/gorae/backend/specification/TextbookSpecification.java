package gorae.backend.specification;

import gorae.backend.constant.textbook.TextbookLevel;
import gorae.backend.dto.textbook.TextbookSearchRequestDto;
import gorae.backend.entity.textbook.Textbook;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;
import java.util.Set;

public class TextbookSpecification {
    private TextbookSpecification() {
        throw new IllegalStateException("Specification class");
    }

    public static Specification<Textbook> searchByCondition(TextbookSearchRequestDto dto) {
        return ((root, query, criteriaBuilder) -> {
            Objects.requireNonNull(query).distinct(true);
            return Specification.where(withWord(dto.word()))
                    .and(withTags(dto.tags()))
                    .and(withLevel(dto.level()))
                    .toPredicate(root, query, criteriaBuilder);
        });
    }

    private static Specification<Textbook> withWord(String word) {
        return ((root, query, criteriaBuilder) -> {
            if (word == null || word.trim().isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                    "%" + word.toLowerCase() + "%");
        });
    }

    private static Specification<Textbook> withTags(Set<String> tags) {
        return ((root, query, criteriaBuilder) -> {
            if (tags == null || tags.isEmpty()) {
                return null;
            }

            Subquery<Long> subquery = Objects.requireNonNull(query).subquery(Long.class);
            Root<Textbook> subRoot = subquery.from(Textbook.class);
            Join<Textbook, String> tagJoin = subRoot.join("tags");

            subquery.select(subRoot.get("id"))
                    .where(tagJoin.in(tags))
                    .groupBy(subRoot.get("id"))
                    .having(criteriaBuilder.equal(
                            criteriaBuilder.countDistinct(tagJoin),
                            tags.size()
                    ));

            return root.get("id").in(subquery);
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
