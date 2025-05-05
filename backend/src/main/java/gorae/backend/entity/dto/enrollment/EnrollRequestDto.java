package gorae.backend.entity.dto.enrollment;

public record EnrollRequestDto(
        Long courseId,
        Long ticketId
) {
}
