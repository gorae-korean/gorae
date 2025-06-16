package gorae.backend.config;

import gorae.backend.common.ProfileUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OAuth2Config {
    @Bean
    public OAuth2AuthorizationRequestResolver customAuthorizationRequestResolver(
            ClientRegistrationRepository clientRegistrationRepository
    ) {
        DefaultOAuth2AuthorizationRequestResolver defaultResolver =
                new DefaultOAuth2AuthorizationRequestResolver(
                        clientRegistrationRepository,
                        "/oauth2/authorization");

        return new OAuth2AuthorizationRequestResolver() {
            @Override
            public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
                OAuth2AuthorizationRequest defaultRequest = defaultResolver.resolve(request);
                if (defaultRequest == null) {
                    return null;
                }

                return OAuth2AuthorizationRequest.from(defaultRequest)
                        .additionalParameters(params -> {
                            params.put("access_type", "offline");
                            params.put("prompt", "consent");
                        })
                        .build();
            }

            @Override
            public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
                OAuth2AuthorizationRequest defaultRequest = defaultResolver.resolve(request, clientRegistrationId);
                if (defaultRequest == null) {
                    return null;
                }

                if (clientRegistrationId.equals("google")) {
                    return OAuth2AuthorizationRequest.from(defaultRequest)
                            .additionalParameters(params -> {
                                params.put("access_type", "offline");
                                params.put("prompt", "consent");
                            })
                            .build();
                }

                return defaultRequest;
            }
        };
    }
}
