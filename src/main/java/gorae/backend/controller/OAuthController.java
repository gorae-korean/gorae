package gorae.backend.controller;

import gorae.backend.common.JwtUtils;
import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.auth.GoogleScopeRequestDto;
import gorae.backend.dto.auth.GoogleScopeResponseDto;
import gorae.backend.service.auth.OAuthService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
@Tag(name = "Google에 토큰 및 권한 요청 API")
public class OAuthController {
    private final OAuthService authService;

    @PostMapping("/scope/google")
    public ResponseEntity<ResponseDto<GoogleScopeResponseDto>> requestScopes(
            Authentication authentication,
            @RequestBody GoogleScopeRequestDto requestDto
    ) {
        if (requestDto.requiredScopes() == null) {
            Set<String> scopes = authService.getDefaultScopes();
            requestDto = new GoogleScopeRequestDto(scopes);
        }

        String publicId = JwtUtils.getPublicId(authentication);
        log.info("[API] RequestScopes requested from {}", publicId);

        GoogleScopeResponseDto responseDto = authService.generateAuthUri(publicId, requestDto);
        return ResponseEntity.ok(ResponseDto.createSuccess(responseDto));
    }

    @Hidden
    @GetMapping("/callback/google")
    public ResponseEntity<ResponseDto<Void>> authCallback(
            Authentication authentication,
            @RequestParam UUID state, @RequestParam String code, @RequestParam String scope
    ) {
        String userId = JwtUtils.getId(authentication);
        String publicId = JwtUtils.getPublicId(authentication);
        log.info("[API] AuthCallback requested from {}", publicId);

        authService.handleCallback(userId, state, code, scope);
        return ResponseEntity.ok(ResponseDto.createSuccess());
    }
}
