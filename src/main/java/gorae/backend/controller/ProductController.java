package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.product.ProductDto;
import gorae.backend.service.ProductService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<ResponseDto<List<ProductDto>>> getProducts() {
        List<ProductDto> products = productService.getProducts();
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, products));
    }

    @CommonApiResponses(summary = "특정 상품 정보 조회")
    @ApiResponse(responseCode = "200", description = "특정 상품 정보 조회 성공")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductDto>> getProduct(@PathVariable UUID id) {
        ProductDto product = productService.getProduct(id);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, product));
    }
}
