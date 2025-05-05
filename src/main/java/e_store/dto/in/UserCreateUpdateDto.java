package e_store.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateUpdateDto(
        @NotBlank
        String firstName,
        String lastName,
        @Email
        @NotBlank
        String email
) {
}
