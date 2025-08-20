package gorae.backend.entity;

import gorae.backend.common.DiscountCalculator;
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

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal discountedPrice;

    @Column(nullable = false)
    private String currencyCode;

    @Column(nullable = false)
    private int count;

    public ProductDto toDto() {
        return ProductDto.builder()
                .id(getPublicId())
                .name(name.getKey())
                .price(price)
                .discountedPrice(discountedPrice)
                .discountPercentage(DiscountCalculator.getDiscountPercentage(price, discountedPrice))
                .currencyCode(currencyCode)
                .count(count)
                .hasDiscount(DiscountCalculator.getHasDiscount(price, discountedPrice))
                .build();
    }
}
