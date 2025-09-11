package gorae.backend.config;

import gorae.backend.security.CustomAuthenticationEntryPoint;
import gorae.backend.security.jwt.JwtAuthFilter;
import gorae.backend.security.oauth.OAuthLoginSuccessHandler;
import gorae.backend.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuthLoginSuccessHandler oAuthLoginSuccessHandler;
    private final OAuth2AuthorizationRequestResolver customAuthorizationRequestResolver;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);

        MvcRequestMatcher[] permitAllWhiteList = {
                mvc.pattern("/checkouts/cancel/**"),
                mvc.pattern("/products"),
                mvc.pattern("/webhooks/**"),
                mvc.pattern("/textbooks"),
                mvc.pattern("/textbooks/categories"),
                mvc.pattern("/oauth/code/**"),
                mvc.pattern("/favicon.ico"),
                mvc.pattern("/actuator/health"),
                mvc.pattern("/instructors")
        };

        MvcRequestMatcher[] swaggerPaths = {
                mvc.pattern("/v3/api-docs/**"),
                mvc.pattern("/swagger-ui/**")
        };

        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

        http.oauth2Login(oauth2 -> {
                    oauth2.authorizationEndpoint(auth ->
                            auth.authorizationRequestResolver(customAuthorizationRequestResolver));
                    oauth2.userInfoEndpoint(userInfo ->
                            userInfo.userService(customOAuth2UserService));
                    oauth2.successHandler(oAuthLoginSuccessHandler);
                    oauth2.authorizedClientService(oAuth2AuthorizedClientService);
                    oauth2.authorizedClientRepository(oAuth2AuthorizedClientRepository);
                }
        );

        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
            auth.requestMatchers(permitAllWhiteList).permitAll();
            auth.requestMatchers(swaggerPaths).permitAll();
            auth.anyRequest().authenticated();
        });

        http.exceptionHandling(exception ->
                exception.authenticationEntryPoint(customAuthenticationEntryPoint));

        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
