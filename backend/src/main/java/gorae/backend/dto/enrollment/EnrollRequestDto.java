package gorae.backend.dto.enrollment;

public record EnrollRequestDto(
        Long courseId,
        Long ticketId
) {
}
