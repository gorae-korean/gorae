package gorae.backend.entity;

import gorae.backend.dto.textbook.TextbookDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Textbook extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @ElementCollection
    @CollectionTable(
            name = "textbook_tags",
            joinColumns = @JoinColumn(name = "textbook_id")
    )
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Instructor instructor;

    public TextbookDto toDto() {
        return TextbookDto.builder()
                .id(this.getId())
                .tags(tags)
                .title(title)
                .instructorName(instructor.getName())
                .build();
    }
}
