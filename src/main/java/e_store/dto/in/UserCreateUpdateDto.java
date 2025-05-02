package e_store.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateUpdateDto(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @Email
        String email
) {
}
