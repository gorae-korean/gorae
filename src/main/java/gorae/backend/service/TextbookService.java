package gorae.backend.service;

import gorae.backend.dto.textbook.*;
import gorae.backend.entity.textbook.*;
import gorae.backend.entity.textbook.key_expression.TextbookKeyExpression;
import gorae.backend.entity.textbook.play_a_role.TextbookPlayARoleScene;
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
import java.util.stream.IntStream;

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
        List<TextbookVocabulary> vocabularies = textbookRepository.findByPublicId(publicId)
                .orElseThrow(() -> new CustomException(ErrorStatus.TEXTBOOK_NOT_FOUND))
                .getVocabularies();

        return IntStream.range(0, vocabularies.size())
                .mapToObj(i -> vocabularies.get(i).toDto(i + 1))
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
        List<TextbookLessonQuestion> questions = textbookRepository.findByPublicId(publicId)
                .orElseThrow(() -> new CustomException(ErrorStatus.TEXTBOOK_NOT_FOUND))
                .getLessonQuestions();

        return IntStream.range(0, questions.size())
                .mapToObj(i -> questions.get(i).toDto(i + 1))
                .toList();
    }

    public List<TextbookCultureTipDto> getCultureTips(UUID publicId) {
        List<TextbookCultureTip> cultureTips = textbookRepository.findByPublicId(publicId)
                .orElseThrow(() -> new CustomException(ErrorStatus.TEXTBOOK_NOT_FOUND))
                .getCultureTips();

        return IntStream.range(0, cultureTips.size())
                .mapToObj(i -> cultureTips.get(i).toDto(i + 1))
                .toList();
    }

    public TextbookRealTalkDto getRealTalk(UUID publicId) {
        Textbook textbook = textbookRepository.findByPublicId(publicId)
                .orElseThrow(() -> new CustomException(ErrorStatus.TEXTBOOK_NOT_FOUND));

        List<TextbookPlayARoleSceneDto> playARoleScenes = textbook.getPlayARoles()
                .stream().map(TextbookPlayARoleScene::toDto)
                .toList();

        List<TextbookLetsTalkAboutDto> letsTalkAbouts = textbook.getLetsTalkAbouts()
                .stream().map(TextbookLetsTalkAbout::toDto)
                .toList();

        return new TextbookRealTalkDto(playARoleScenes, letsTalkAbouts);
    }
}
