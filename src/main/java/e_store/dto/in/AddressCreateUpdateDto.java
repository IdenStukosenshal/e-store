package e_store.dto.in;

import jakarta.validation.constraints.NotBlank;

public record AddressCreateUpdateDto(
        @NotBlank
        String city,
        @NotBlank
        String postalCode,
        @NotBlank
        String streetAddress
) {

}
