package e_store.dto.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Map;

public record OrderCreateUpdateDto(
        @NotNull
        Long userId,
        @NotNull
        @Valid
        AddressCreateDto addressCreateDto,
        @NotNull
        @Size(min = 1, max = 1000, message = "min 1")
        Map<Long, Integer> productQuantityIdMap
) {
}
