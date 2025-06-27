package gorae.backend.entity.textbook.play_a_role;

import gorae.backend.dto.textbook.TextbookPlayARoleSceneDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "play_a_role_scene")
public class TextbookPlayARoleScene {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(
            name = "play_a_role_scene_character",
            joinColumns = @JoinColumn(name = "scene_id")
    )
    private List<Character> characters = new ArrayList<>();

    @URL
    @Column(length = 2048)
    private String imageUrl;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotEmpty
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "scene_id")
    List<TextbookPlayARoleScript> scripts = new ArrayList<>();

    public TextbookPlayARoleSceneDto toDto() {
        return TextbookPlayARoleSceneDto.builder()
                .title(title)
                .imageUrl(imageUrl)
                .scripts(IntStream.range(0, scripts.size())
                        .mapToObj(i -> scripts.get(i).toDto(i + 1))
                        .toList())
                .characters(characters)
                .build();
    }
}
