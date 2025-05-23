package gorae.backend.entity;

import gorae.backend.constant.AuthProvider;
import gorae.backend.constant.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(name = "member_type")
@SuperBuilder
public class Member extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String picture;

    private String password;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    public Member update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }
}
