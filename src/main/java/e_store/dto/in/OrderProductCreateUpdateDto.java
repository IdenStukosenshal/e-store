package e_store.dto.in;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderProductCreateUpdateDto(
        @NotNull
        Long productId,
        @Min(1)
        Long quantity
) {
}
