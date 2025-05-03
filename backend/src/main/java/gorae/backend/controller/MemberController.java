package gorae.backend.controller;

import gorae.backend.entity.dto.ResponseDto;
import gorae.backend.entity.dto.ResponseStatus;
import gorae.backend.entity.dto.member.LoginRequestDto;
import gorae.backend.entity.dto.member.SignupRequestDto;
import gorae.backend.entity.dto.member.TokenDto;
import gorae.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<TokenDto>> signup(@RequestBody SignupRequestDto dto) {
        log.info("[API] Signup requested: {}", dto.email());
        TokenDto tokenDto = memberService.signup(dto);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, tokenDto));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<TokenDto>> login(@RequestBody LoginRequestDto dto) {
        log.info("[API] Login requested: {}", dto.email());
        TokenDto tokenDto = memberService.login(dto);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, tokenDto));
    }
}
