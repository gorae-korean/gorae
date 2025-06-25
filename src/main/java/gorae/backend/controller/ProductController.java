package gorae.backend.controller;

import gorae.backend.common.JwtUtils;
import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.product.ProductDto;
import gorae.backend.service.ProductService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "상품 API")
public class ProductController {
    private final ProductService productService;

    @CommonApiResponses(summary = "상품 목록 조회")
    @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공")
    @GetMapping
    public ResponseEntity<ResponseDto<List<ProductDto>>> getProducts(Authentication authentication) {
        log.info("[API] GetProducts requested: {}", JwtUtils.getSubject(authentication));
        List<ProductDto> products = productService.getProducts();
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, products));
    }
}
