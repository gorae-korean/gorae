package gorae.backend.service;

import gorae.backend.dto.textbook.*;
import gorae.backend.entity.textbook.Textbook;
import gorae.backend.entity.textbook.TextbookLessonQuestion;
import gorae.backend.entity.textbook.TextbookVocabulary;
import gorae.backend.entity.textbook.key_expression.TextbookKeyExpression;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import gorae.backend.repository.TextbookRepository;
import gorae.backend.specification.TextbookSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TextbookService {
    private final TextbookRepository textbookRepository;

    public List<TextbookSearchDto> searchTextbooks(TextbookSearchRequestDto dto) {
        return textbookRepository.findAll(
                        TextbookSpecification.searchByCondition(dto)
                ).stream().map(Textbook::toDto)
                .toList();
    }

    public TextbookArticleDto getArticle(UUID publicId) {
        return textbookRepository.findByPublicId(publicId)
                .orElseThrow(() -> new CustomException(ErrorStatus.TEXTBOOK_NOT_FOUND))
                .toArticleDto();
    }

    public List<TextbookVocabularyDto> getVocabularies(UUID publicId) {
        return textbookRepository.findByPublicId(publicId)
                .orElseThrow(() -> new CustomException(ErrorStatus.TEXTBOOK_NOT_FOUND))
                .getVocabularies()
                .stream().map(TextbookVocabulary::toDto)
                .toList();
    }

    public List<TextbookKeyExpressionDto> getKeyExpressions(UUID publicId) {
        return textbookRepository.findByPublicId(publicId)
                .orElseThrow(() -> new CustomException(ErrorStatus.TEXTBOOK_NOT_FOUND))
                .getKeyExpressions()
                .stream().map(TextbookKeyExpression::toDto)
                .toList();
    }

    public List<TextbookLessonQuestionDto> getLessonQuestions(UUID publicId) {
        return textbookRepository.findByPublicId(publicId)
                .orElseThrow(() -> new CustomException(ErrorStatus.TEXTBOOK_NOT_FOUND))
                .getLessonQuestions()
                .stream().map(TextbookLessonQuestion::toDto)
                .toList();
    }
}
