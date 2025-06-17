package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.textbook.TextbookSearchRequestDto;
import gorae.backend.dto.textbook.TextbookDto;
import gorae.backend.service.TextbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gorae.backend.common.JwtUtils.getUserId;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/textbooks")
public class TextbookController {
    private final TextbookService textbookService;

    @PostMapping("/search")
    public ResponseEntity<ResponseDto<List<TextbookDto>>> searchTextbooks(
            Authentication authentication,
            @RequestBody TextbookSearchRequestDto requestDto
    ) {
        String userId = getUserId(authentication);
        log.info("[API] GetTextbooks requested: {}", userId);
        log.debug("Received Tags: {}", requestDto.tags());
        List<TextbookDto> textbooks = textbookService.searchTextbooks(requestDto);
        return ResponseEntity.ok().body(new ResponseDto<>(ResponseStatus.SUCCESS, textbooks));
    }
}
