package gorae.backend.entity;

import gorae.backend.constant.MemberRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public abstract class Member extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String picture;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private String provider;

    private String oauthId;

    public Member update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    protected void setRole(MemberRole role) {
        this.role = role;
    }
}
