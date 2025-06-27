package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.textbook.TextbookDto;
import gorae.backend.dto.textbook.TextbookSearchRequestDto;
import gorae.backend.service.TextbookService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static gorae.backend.common.JwtUtils.getSubject;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/textbooks")
@Tag(name = "교재 API")
public class TextbookController {
    private final TextbookService textbookService;

    @CommonApiResponses(
            summary = "교재 검색",
            description = """
                    다양한 조건으로 교재 필터링이 가능합니다.<br>\
                    필요 없는 검색 조건은 제외할 수 있습니다.<br>\
                    아무런 조건을 넣지 않은 빈 객체를 제출하면, 모든 교재가 나타납니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "교재 검색 성공")
    @GetMapping
    public ResponseEntity<ResponseDto<List<TextbookDto>>> searchTextbooks(
            Authentication authentication,
            @ModelAttribute TextbookSearchRequestDto searchRequest
    ) {
        log.info("[API] GetTextbooks requested: {}", getSubject(authentication));
        log.debug(searchRequest.toString());
        List<TextbookDto> textbooks = textbookService.searchTextbooks(searchRequest);
        return ResponseEntity.ok().body(new ResponseDto<>(ResponseStatus.SUCCESS, textbooks));
    }
}
