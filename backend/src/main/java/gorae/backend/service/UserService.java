package gorae.backend.service;

import gorae.backend.entity.Instructor;
import gorae.backend.entity.Student;
import gorae.backend.entity.Member;
import gorae.backend.entity.dto.user.LoginRequestDto;
import gorae.backend.entity.dto.user.SignupRequestDto;
import gorae.backend.entity.dto.user.TokenDto;
import gorae.backend.entity.repository.InstructorRepository;
import gorae.backend.entity.repository.StudentRepository;
import gorae.backend.entity.repository.MemberRepository;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import gorae.backend.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final MemberRepository memberRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public TokenDto signup(SignupRequestDto dto) {
        if (memberRepository.existsByEmail(dto.email())) {
            throw new CustomException(ErrorStatus.EMAIL_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(dto.password());
        Member savedMember;

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
            }
            case INSTRUCTOR -> {
                Instructor instructor = Instructor.builder()
                        .name(dto.name())
                        .email(dto.email())
                        .password(encodedPassword)
                        .phoneNumber(dto.phoneNumber())
                        .build();
                savedMember = instructorRepository.save(instructor);
            }
            default -> throw new IllegalStateException("Unexpected value: " + dto.accountType());
        }

        log.info("User signed up: {}", savedMember.getEmail());
        String token = jwtTokenUtil.generateToken(savedMember);
        return new TokenDto(token);
    }

    public TokenDto login(LoginRequestDto dto) {
        Member member;
        switch (dto.accountType()) {
            case STUDENT -> member = studentRepository.findByEmail(dto.email())
                    .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));
            case INSTRUCTOR -> member = instructorRepository.findByEmail(dto.email())
                    .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));
            default -> throw new IllegalStateException("Unexpected value: " + dto.accountType());
        }

        if (!passwordEncoder.matches(dto.password(), member.getPassword())) {
            throw new CustomException(ErrorStatus.WRONG_CREDENTIAL);
        }

        log.info("User logged in: {}", member.getEmail());
        String token = jwtTokenUtil.generateToken(member);
        return new TokenDto(token);
    }
}
