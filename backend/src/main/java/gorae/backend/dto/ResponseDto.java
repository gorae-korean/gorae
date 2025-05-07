package gorae.backend.dto;

public record ResponseDto<T>(ResponseStatus status, T data) {
}
