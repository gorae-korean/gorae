package gorae.backend.security.oauth;

import gorae.backend.constant.AuthProvider;
import gorae.backend.entity.Student;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private AuthProvider provider;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if (registrationId.equals("google")) {
            return ofGoogle(userNameAttributeName, attributes);
        } else {
            throw new CustomException(ErrorStatus.WRONG_PROVIDER);
        }
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .provider(AuthProvider.GOOGLE)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Student toEntity() {
        return Student.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .provider(provider)
                .isFirst(true)
                .build();
    }
}
