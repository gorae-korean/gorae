package gorae.backend.entity.textbook;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "lets_talk_about")
public class TextbookLetsTalkAbout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @ElementCollection
    @CollectionTable(
            name = "lets_talk_about_example",
            joinColumns = @JoinColumn(name = "lets_talk_about_id")
    )
    @Column(name = "discussion_point")
    private List<String> discussionPoints = new ArrayList<>();

    @URL
    @Column(length = 2048)
    private String imageUrl;

    @NotBlank
    @Column(nullable = false)
    private String title;
}
