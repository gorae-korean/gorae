package gorae.backend.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.member.TokenDto;
import gorae.backend.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String token = jwtTokenProvider.generateToken(authentication);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        TokenDto tokenDto = new TokenDto(token);

        String jsonResponse = objectMapper.writeValueAsString(new ResponseDto<>(ResponseStatus.SUCCESS, tokenDto));
        response.getWriter().write(jsonResponse);
    }
}
