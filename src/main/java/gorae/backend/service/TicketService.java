package gorae.backend.service;

import gorae.backend.dto.ticket.TicketDto;
import gorae.backend.entity.Student;
import gorae.backend.entity.Ticket;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import gorae.backend.repository.StudentRepository;
import gorae.backend.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final StudentRepository studentRepository;

    public List<TicketDto> getTickets(String userId) {
        Long studentId = Long.valueOf(userId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));

        return ticketRepository.findByStudent(student)
                .stream().map(Ticket::toDto)
                .toList();
    }
}
