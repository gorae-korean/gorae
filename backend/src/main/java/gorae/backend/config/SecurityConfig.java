package gorae.backend.config;

import gorae.backend.common.ProfileUtils;
import gorae.backend.security.jwt.JwtAuthFilter;
import gorae.backend.security.oauth.OAuthLoginSuccessHandler;
import gorae.backend.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final ProfileUtils profileUtils;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuthLoginSuccessHandler oAuthLoginSuccessHandler;
    private final OAuth2AuthorizationRequestResolver customAuthorizationRequestResolver;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        if (profileUtils.isDevMode()) {
            configuration.setAllowedOrigins(List.of("*"));
            configuration.setAllowedMethods(List.of("*"));
            configuration.setAllowedHeaders(List.of("*"));
            configuration.setExposedHeaders(List.of("*"));
        }

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);

        MvcRequestMatcher[] permitAllWhiteList = {
                mvc.pattern("/api/members/signup"),
                mvc.pattern("/api/members/login"),
                mvc.pattern("/api/checkouts/complete/**"),
                mvc.pattern("/api/checkouts/cancel/**"),
                mvc.pattern("/oauth/**")
        };

        MvcRequestMatcher[] swaggerPaths = {
                mvc.pattern("/v3/api-docs/**"),
                mvc.pattern("/swagger-ui/**"),
                mvc.pattern("/swagger-ui.html"),
                mvc.pattern("/swagger-resources/**"),
                mvc.pattern("/webjars/**")
        };

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.csrf(AbstractHttpConfigurer::disable);

        http.oauth2Login(oauth2 -> {
                    oauth2.authorizationEndpoint(auth ->
                            auth.authorizationRequestResolver(customAuthorizationRequestResolver));
                    oauth2.userInfoEndpoint(userInfo ->
                            userInfo.userService(customOAuth2UserService));
                    oauth2.successHandler(oAuthLoginSuccessHandler);
                }
        );

        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(permitAllWhiteList).permitAll();
            if (profileUtils.isDevMode()) {
                auth.requestMatchers(swaggerPaths).permitAll();
            }
            auth.anyRequest().authenticated();
        });

        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        return http.build();
    }
}
