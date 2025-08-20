package gorae.backend.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class DiscountCalculator {
    private DiscountCalculator() {
        throw new IllegalStateException("Utility class");
    }

    public static BigDecimal getDiscountPercentage(BigDecimal price, BigDecimal discountedPrice) {
        if (price == null || discountedPrice == null || price.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }

        if (discountedPrice.compareTo(price) >= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal discountAmount = price.subtract(discountedPrice);
        return discountAmount.divide(price, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(0, RoundingMode.HALF_UP);
    }

    public static boolean getHasDiscount(BigDecimal price, BigDecimal discountedPrice) {
        return discountedPrice != null && discountedPrice.compareTo(price) < 0;
    }
}
