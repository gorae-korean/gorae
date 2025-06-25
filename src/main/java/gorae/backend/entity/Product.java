package gorae.backend.entity;

import gorae.backend.constant.ProductName;
import gorae.backend.dto.product.ProductDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private ProductName name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String currencyCode;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private boolean isAvailable = true;

    public ProductDto toDto() {
        return ProductDto.builder()
                .id(getPublicId())
                .name(name)
                .price(price)
                .currencyCode(currencyCode)
                .count(count)
                .build();
    }
}
