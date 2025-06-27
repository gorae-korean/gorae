package gorae.backend.service;

import gorae.backend.dto.textbook.TextbookArticleDto;
import gorae.backend.dto.textbook.TextbookSearchDto;
import gorae.backend.dto.textbook.TextbookSearchRequestDto;
import gorae.backend.entity.textbook.Textbook;
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
        return textbookRepository.findByPublicId(publicId).toArticleDto();
    }
}
