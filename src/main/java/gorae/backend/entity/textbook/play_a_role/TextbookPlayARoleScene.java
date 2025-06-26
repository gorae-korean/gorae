package gorae.backend.entity.textbook.play_a_role;

import gorae.backend.entity.BaseEntity;
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
@Table(name = "play_a_role_scene")
public class TextbookPlayARoleScene extends BaseEntity {
    @ElementCollection
    @CollectionTable(
            name = "play_a_role_scene_character",
            joinColumns = @JoinColumn(name = "scene_id")
    )
    private List<Character> characters = new ArrayList<>();

    @NotBlank
    @URL
    @Column(nullable = false)
    private String imageUrl;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotEmpty
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "scene_id")
    List<TextbookPlayARoleScript> scripts = new ArrayList<>();
}
