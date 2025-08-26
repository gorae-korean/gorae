package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.ticket.TicketDto;
import gorae.backend.service.TicketService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gorae.backend.common.JwtUtils.getId;
import static gorae.backend.common.JwtUtils.getSubject;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
@Tag(name = "수강권 API")
public class TicketController {
    private final TicketService ticketService;

    @CommonApiResponses(summary = "수강권 목록 조회")
    @ApiResponse(responseCode = "200", description = "수강권 목록 조회 성공")
    @GetMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ResponseDto<List<TicketDto>>> getTickets(Authentication authentication) {
        String subject = getSubject(authentication);
        log.info("[API] GetTickets requested from sub: {}", subject);

        List<TicketDto> tickets = ticketService.getTickets(getId(authentication));
        log.info("[API] GetTickets responded to sub: {}", subject);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, tickets));
    }

    @CommonApiResponses(summary = "수강권 개수 조회")
    @ApiResponse(responseCode = "200", description = "수강권 개수 조회 성공")
    @GetMapping("/count")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ResponseDto<Long>> getTicketCount(
            Authentication authentication,
            @Parameter(description = "강의 규모", example = "2")
            @RequestParam int ratio) {
        String subject = getSubject(authentication);
        log.info("[API] GetTicketCount requested from sub: {}", subject);

        long ticketCount = ticketService.getTicketCount(getId(authentication), ratio);
        log.info("[API] GetTicketCount responded to sub: {}", subject);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, ticketCount));
    }
}
