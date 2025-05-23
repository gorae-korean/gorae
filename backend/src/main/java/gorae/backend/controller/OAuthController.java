package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.member.TokenDto;
import gorae.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    private final MemberService memberService;

    @GetMapping("/callback/{provider}")
    public ResponseEntity<ResponseDto<TokenDto>> callback(@PathVariable(name = "provider") String provider) {

        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, new TokenDto("hi")));
    }
}
