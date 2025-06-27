package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.textbook.*;
import gorae.backend.service.TextbookService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<ResponseDto<List<TextbookSearchDto>>> searchTextbooks(
            Authentication authentication,
            @ModelAttribute TextbookSearchRequestDto searchRequest
    ) {
        log.info("[API] GetTextbooks requested: {}", getSubject(authentication));
        log.debug(searchRequest.toString());
        List<TextbookSearchDto> textbooks = textbookService.searchTextbooks(searchRequest);
        return ResponseEntity.ok().body(new ResponseDto<>(ResponseStatus.SUCCESS, textbooks));
    }

    @CommonApiResponses(
            summary = "교재 본문 조회",
            description = "교재의 'Article' 을 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "교재 본문 조회 성공")
    @GetMapping("/{id}/article")
    public ResponseEntity<ResponseDto<TextbookArticleDto>> getArticle(
            Authentication authentication,
            @Parameter(description = "교재 ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id
    ) {
        log.info("[API] GetArticle requested: {}", getSubject(authentication));
        TextbookArticleDto article = textbookService.getArticle(id);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, article));
    }

    @CommonApiResponses(
            summary = "교재 핵심 단어 조회",
            description = "교재의 'Key Vocabulary' 를 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "교재 핵심 단어 조회 성공")
    @GetMapping("/{id}/vocabularies")
    public ResponseEntity<ResponseDto<List<TextbookVocabularyDto>>> getVocabularies(
            Authentication authentication,
            @Parameter(description = "교재 ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id
    ) {
        log.info("[API] GetVocabularies requested: {}", getSubject(authentication));
        List<TextbookVocabularyDto> vocabularies = textbookService.getVocabularies(id);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, vocabularies));
    }

    @CommonApiResponses(
            summary = "교재 핵심 표현 조회",
            description = "교재의 'Key Expressions' 를 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "교재 핵심 표현 조회 성공")
    @GetMapping("/{id}/expressions")
    public ResponseEntity<ResponseDto<List<TextbookKeyExpressionDto>>> getKeyExpressions(
            Authentication authentication,
            @Parameter(description = "교재 ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id
    ) {
        log.info("[API] GetKeyExpressions requested: {}", getSubject(authentication));
        List<TextbookKeyExpressionDto> keyExpressions = textbookService.getKeyExpressions(id);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, keyExpressions));
    }
}
