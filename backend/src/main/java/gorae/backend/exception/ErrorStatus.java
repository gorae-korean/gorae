package gorae.backend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {
    // 400 BAD_REQUEST
    ALREADY_ENROLLED(HttpStatus.BAD_REQUEST, "이미 신청되어 있습니다."),
    AVAILABILITY_OVERLAPPED(HttpStatus.BAD_REQUEST, "기존 일정과 겹칩니다."),
    CANNOT_BUY_THE_FIRST_PRODUCT(HttpStatus.BAD_REQUEST, "첫 구매 혜택이 이미 사용되었습니다."),
    CANNOT_DROP_COURSE_NEAR_START_TIME(HttpStatus.BAD_REQUEST, "강의 시작 시간이 얼마 남지 않아 취소가 불가능합니다."),
    COURSE_ALREADY_STARTED(HttpStatus.BAD_REQUEST, "이미 시작된 강좌입니다."),
    COURSE_IS_FULL(HttpStatus.BAD_REQUEST, "해당 강좌 인원이 모두 찼습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이메일이 이미 존재합니다."),
    INVALID_DROP(HttpStatus.BAD_REQUEST, "이미 취소되었거나 수강이 완료된 신청입니다."),
    MUST_BE_LONGER_THAN_60_MINUTES(HttpStatus.BAD_REQUEST, "두 시간의 차이가 60분 이상이어야 합니다."),
    TICKET_ALREADY_USED(HttpStatus.BAD_REQUEST, "이미 사용된 수강권입니다."),
    TICKET_IS_NOT_VALID(HttpStatus.BAD_REQUEST, "유효하지 않은 수강권입니다."),
    WRONG_CREDENTIAL(HttpStatus.BAD_REQUEST, "이메일 또는 비밀번호가 잘못되었습니다."),
    WRONG_TIME(HttpStatus.BAD_REQUEST, "시작 시간이 종료 시간보다 늦을 수 없습니다."),

    // 403 FORBIDDEN
    NO_PERMISSIONS(HttpStatus.FORBIDDEN, "해당 요청에 대한 권한이 없습니다."),

    // 404 NOT_FOUND
    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "강좌가 존재하지 않습니다."),
    ENROLLMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "수강신청이 존재하지 않습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문 내역이 존재하지 않습니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품이 존재하지 않습니다."),
    TICKET_NOT_FOUND(HttpStatus.NOT_FOUND, "수강권이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
