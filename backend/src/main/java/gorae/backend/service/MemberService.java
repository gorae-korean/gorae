package gorae.backend.service;

import gorae.backend.entity.Instructor;
import gorae.backend.entity.Student;
import gorae.backend.entity.Member;
import gorae.backend.dto.member.LoginRequestDto;
import gorae.backend.dto.member.SignupRequestDto;
import gorae.backend.dto.member.TokenDto;
import gorae.backend.repository.InstructorRepository;
import gorae.backend.repository.StudentRepository;
import gorae.backend.repository.MemberRepository;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import gorae.backend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenDto signup(SignupRequestDto dto) {
        if (memberRepository.existsByEmail(dto.email())) {
            throw new CustomException(ErrorStatus.EMAIL_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(dto.password());
        Member savedMember;
        List<String> roles;

        switch (dto.accountType()) {
            case STUDENT -> {
                Student student = Student.builder()
                        .name(dto.name())
                        .email(dto.email())
                        .password(encodedPassword)
                        .phoneNumber(dto.phoneNumber())
                        .isFirst(true)
                        .build();
                savedMember = studentRepository.save(student);
                roles = List.of("ROLE_STUDENT");
            }
            case INSTRUCTOR -> {
                Instructor instructor = Instructor.builder()
                        .name(dto.name())
                        .email(dto.email())
                        .password(encodedPassword)
                        .phoneNumber(dto.phoneNumber())
                        .build();
                savedMember = instructorRepository.save(instructor);
                roles = List.of("ROLE_INSTRUCTOR");
            }
            default -> throw new IllegalStateException("Unexpected value: " + dto.accountType());
        }

        log.info("User signed up: {}", savedMember.getEmail());
        String token = jwtTokenProvider.generateToken(savedMember, roles);
        return new TokenDto(token);
    }

    public TokenDto login(LoginRequestDto dto) {
        Member member;
        List<String> roles;
        switch (dto.accountType()) {
            case STUDENT -> {
                member = studentRepository.findByEmail(dto.email())
                        .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));
                roles = List.of("ROLE_STUDENT");
            }
            case INSTRUCTOR -> {
                member = instructorRepository.findByEmail(dto.email())
                        .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));
                roles = List.of("ROLE_INSTRUCTOR");
            }
            default -> throw new IllegalStateException("Unexpected value: " + dto.accountType());
        }

        if (!passwordEncoder.matches(dto.password(), member.getPassword())) {
            throw new CustomException(ErrorStatus.WRONG_CREDENTIAL);
        }

        log.info("User logged in: {}", member.getEmail());
        String token = jwtTokenProvider.generateToken(member, roles);
        return new TokenDto(token);
    }
}
