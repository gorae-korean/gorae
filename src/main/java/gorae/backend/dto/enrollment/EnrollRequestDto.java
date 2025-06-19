package gorae.backend.dto.enrollment;

import java.util.UUID;

public record EnrollRequestDto(
        UUID courseId,
        UUID ticketId
) {
}
