package e_store.dto.filter;

import io.swagger.v3.oas.annotations.media.Schema;

public record OrderFilter(
        @Schema(description = "filter.order.number")
        String orderNumber,
        @Schema(description = "filter.product.name")
        String productName) {
}
