package gorae.backend.controller;

import gorae.backend.entity.dto.user.LoginRequestDto;
import gorae.backend.entity.dto.user.SignupRequestDto;
import gorae.backend.entity.dto.user.TokenDto;
import gorae.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<TokenDto> signup(@RequestBody SignupRequestDto dto) {
        log.info("[API] Signup requested: {}", dto.email());
        TokenDto tokenDto = userService.signup(dto);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto dto) {
        log.info("[API] Login requested: {}", dto.email());
        TokenDto tokenDto = userService.login(dto);
        return ResponseEntity.ok(tokenDto);
    }
}
