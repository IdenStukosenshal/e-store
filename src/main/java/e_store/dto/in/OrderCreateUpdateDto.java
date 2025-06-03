package e_store.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Map;

public record OrderCreateUpdateDto(
        @NotNull
        Long userId,
        @NotNull
        @Valid
        AddressCreateDto addressCreateDto,
        @NotNull
        @Size(min = 1, max = 1000)
        @Schema(example = """
                        {
                          "1": 5,
                          "2": 10
                        }
                        """
        )
        Map<Long, @Positive(message = "min 1") Integer> productQuantityIdMap
) {
}
