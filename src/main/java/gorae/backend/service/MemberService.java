package gorae.backend.service;

import gorae.backend.constant.TicketStatus;
import gorae.backend.dto.member.MyInfoDto;
import gorae.backend.entity.Member;
import gorae.backend.entity.Student;
import gorae.backend.entity.instructor.Instructor;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import gorae.backend.repository.MemberRepository;
import gorae.backend.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final TicketRepository ticketRepository;

    public MyInfoDto addExtraInfo(String userId, MyInfoDto myInfoDto) {
        long memberId = Long.parseLong(userId);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));

        switch (member) {
            case Student student -> {
                long studentId = student.getId();
                Instant now = Instant.now();

                long twoCount = ticketRepository.countByStudent_IdAndLessonRatioAndStatusAndEndTimeAfter(
                        studentId, 2, TicketStatus.ACTIVE, now);
                long fourCount = ticketRepository.countByStudent_IdAndLessonRatioAndStatusAndEndTimeAfter(
                        studentId, 4, TicketStatus.ACTIVE, now);

                return MyInfoDto.builder()
                        .name(myInfoDto.name())
                        .email(myInfoDto.email())
                        .role(myInfoDto.role())
                        .imageUrl(myInfoDto.imageUrl())
                        .isFirst(student.isFirst())
                        .twoTicketCount(twoCount)
                        .fourTicketCount(fourCount)
                        .build();
            }
            case Instructor ignored -> {
                return myInfoDto;
            }
            default -> throw new IllegalStateException("회원의 역할이 잘못되었습니다.");
        }
    }
}
