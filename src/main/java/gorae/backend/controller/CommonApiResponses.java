package gorae.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation
@ApiResponse(
        responseCode = "401",
        description = "인증되지 않은 요청",
        content = @Content(
                examples = @ExampleObject(
                        value = """
                                {
                                    "status": "ERROR",
                                    "data": {
                                        "message": "인증에 실패했습니다.",
                                        "code": "FAILED_TO_AUTHENTICATE"
                                    }
                                }
                                """
                )
        )
)
@ApiResponse(
        responseCode = "403",
        description = "접근 권한이 없는 요청 (학생이 강사 기능을 사용하는 등의 경우)",
        content = @Content(
                examples = @ExampleObject(
                        value = """
                                {
                                    "status": "ERROR",
                                    "data": {
                                        "message": "해당 요청에 대한 권한이 없습니다.",
                                        "code": "NO_PERMISSIONS"
                                    }
                                }
                                """
                )
        )
)
@ApiResponse(
        responseCode = "500",
        description = "백엔드에서 정의하지 않은 에러 발생",
        content = @Content(
                examples = @ExampleObject(
                        value = """
                                {
                                    "status": "ERROR",
                                    "data": "서버에서 에러가 발생했습니다."
                                }
                                """
                )
        )
)
public @interface CommonApiResponses {
    @AliasFor(annotation = Operation.class)
    String summary() default "";

    @AliasFor(annotation = Operation.class)
    String description() default "";
}
