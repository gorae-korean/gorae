package gorae.backend.dto.ticket;

import gorae.backend.constant.TicketStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
@Schema(description = "수강권")
public record TicketDto(
        @Schema(description = "수강권 ID")
        UUID id,

        @Schema(description = "수강권 유효 기간 (시작일)")
        Instant startTime,

        @Schema(description = "수강권 유효 기간 (종료일)")
        Instant endTime,

        @Schema(description = "수강권 상태")
        TicketStatus status
) {
}
