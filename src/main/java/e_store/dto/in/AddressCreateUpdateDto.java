package e_store.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressCreateUpdateDto(
        @NotBlank
        String city,
        @NotBlank
        String postalCode,
        @NotBlank
        String streetAddress,
        @NotNull
        Long userId
) {
}
