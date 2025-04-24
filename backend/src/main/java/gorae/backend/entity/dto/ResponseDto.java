package gorae.backend.entity.dto;

public record ResponseDto<T>(ResponseStatus status, T data) {
}
