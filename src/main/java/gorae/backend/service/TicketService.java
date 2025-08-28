package gorae.backend.service;

import gorae.backend.constant.TicketStatus;
import gorae.backend.dto.ticket.TicketDto;
import gorae.backend.entity.Ticket;
import gorae.backend.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public List<TicketDto> getTickets(String userId) {
        long studentId = Long.parseLong(userId);
        return ticketRepository.findByStatusAndStudent_IdAndEndTimeAfter(TicketStatus.ACTIVE, studentId, Instant.now())
                .stream().map(Ticket::toDto)
                .toList();
    }

    public long getTicketCount(String userId, int ratio) {
        long studentId = Long.parseLong(userId);
        return ticketRepository
                .countByStudent_IdAndLessonRatioAndStatusAndEndTimeAfter(studentId, ratio, TicketStatus.ACTIVE, Instant.now());
    }
}
