package gorae.backend.entity;

import gorae.backend.constant.ProductName;
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
}
