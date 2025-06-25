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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
                    다양한 조건으로 교재 필터링 가능
                    필요 없는 검색 조건은 제외하면 됨
                    """
    )
    @ApiResponse(responseCode = "200", description = "교재 검색 성공")
    @PostMapping("/search")
    public ResponseEntity<ResponseDto<List<TextbookDto>>> searchTextbooks(
            Authentication authentication,
            @RequestBody TextbookSearchRequestDto requestDto
    ) {
        log.info("[API] GetTextbooks requested: {}", getSubject(authentication));
        log.debug("Received Tags: {}", requestDto.tags());
        List<TextbookDto> textbooks = textbookService.searchTextbooks(requestDto);
        return ResponseEntity.ok().body(new ResponseDto<>(ResponseStatus.SUCCESS, textbooks));
    }
}
