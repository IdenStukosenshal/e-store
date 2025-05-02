package e_store.dto.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OrderCreateUpdateDto(
        @NotNull
        Long userId,
        @NotNull
        Long addressId,
        @Size(min = 1, max = 1000)
        List<OrderProductCreateUpdateDto> orderProductDtoLst
) {
}
