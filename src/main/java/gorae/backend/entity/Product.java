package gorae.backend.entity;

import gorae.backend.common.DiscountCalculator;
import gorae.backend.constant.ProductName;
import gorae.backend.dto.product.ProductDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @ElementCollection
    @CollectionTable(
            name = "product_benefit",
            joinColumns = @JoinColumn(name = "product_id")
    )
    private List<String> benefits = new ArrayList<>();

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
                .benefits(benefits)
                .build();
    }
}
