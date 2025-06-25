package gorae.backend.service;

import gorae.backend.dto.product.ProductDto;
import gorae.backend.entity.Product;
import gorae.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductDto> getProducts() {
        return productRepository.findAvailable()
                .stream().map(Product::toDto)
                .toList();
    }
}
