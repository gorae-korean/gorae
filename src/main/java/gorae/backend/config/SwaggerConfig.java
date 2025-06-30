package gorae.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Value("${spring.baseurl}")
    private String baseUrl;

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name("auth_token")
                .description("구글 로그인 인증을 완료하면 토큰 키를 담은 쿠키가 저장되어 있습니다.");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("cookieAuth");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("cookieAuth", securityScheme))
                .servers(List.of(new Server().url(baseUrl)))
                .security(List.of(securityRequirement))
                .info(info());
    }

    private static Info info() {
        return new Info()
                .title("Gorae API")
                .description("""
                        Gorae Korean 백엔드 API 문서<br><br>\
                        구글로 로그인 할 때의 링크 <a href="https://api.goraekorean.site/oauth2/authorization/google">
                        https://api.goraekorean.site/oauth2/authorization/google</a><br>\
                        로그인 완료 시 인증 토큰이 파라미터에 추가된 채로 프론트엔드 메인 화면으로 리다이렉트 됩니다.<br><br>\
                        URL에 추가된 토큰을 불러와서 API 요청의 "Authorization" 헤더에 "Bearer 토큰"으로 입력해 사용합니다.<br>\
                        예를 들어 토큰 값이 "4d3"이라면, "Authorization" 헤더에 "Bearer 4d3"을 입력하면 됩니다.
                        """)
                .version("1.0")
                .contact(new Contact().name("김민형").email("realminbros@gmail.com"));
    }
}
