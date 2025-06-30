package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.member.MyInfoDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

import static gorae.backend.common.JwtUtils.getSubject;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "유저 정보 API")
public class MemberController {

    @CommonApiResponses(
            summary = "유저의 기본 정보 조회",
            description = "'role' 의 경우, 'ROLE_STUDENT', 'ROLE_INSTRUCTOR', 'ROLE_ADMIN' 중 하나로 나타납니다."
    )
    @ApiResponse(responseCode = "200", description = "유저 정보 조회 성공")
    @GetMapping("/me")
    public ResponseEntity<ResponseDto<MyInfoDto>> getMyInfo(Authentication authentication) {
        log.info("[API] GetMyInfo requested: {}", getSubject(authentication));
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        List<String> roles = oAuth2User.getAttribute("roles");
        String role = Objects.requireNonNull(roles).getFirst();

        MyInfoDto myInfo = MyInfoDto.builder()
                .email(oAuth2User.getAttribute("email"))
                .name(oAuth2User.getAttribute("name"))
                .imageUrl(oAuth2User.getAttribute("picture"))
                .role(role)
                .build();

        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, myInfo));
    }
}
