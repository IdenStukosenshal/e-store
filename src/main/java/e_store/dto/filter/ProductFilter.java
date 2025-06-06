package e_store.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ProductFilter(
        @Schema(description = "filter.product.name")
        String name,
        @Schema(description = "filter.min.price")
        BigDecimal minPrice,
        @Schema(description = "filter.max.price")
        BigDecimal maxPrice
) {
}
