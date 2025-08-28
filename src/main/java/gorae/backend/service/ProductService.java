package gorae.backend.service;

import gorae.backend.dto.product.ProductDto;
import gorae.backend.entity.Product;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import gorae.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductDto> getProducts() {
        return productRepository.findAllOrderByPrice()
                .stream().map(Product::toDto)
                .toList();
    }

    public ProductDto getProduct(UUID id) {
        return productRepository.findByPublicId(id)
                .orElseThrow(() -> new CustomException(ErrorStatus.PRODUCT_NOT_FOUND))
                .toDto();
    }
}
