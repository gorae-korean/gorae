package gorae.backend.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
    STUDENT("ROLE_STUDENT"),
    INSTRUCTOR("ROLE_INSTRUCTOR"),
    ADMIN("ROLE_ADMIN");

    private final String key;
}
