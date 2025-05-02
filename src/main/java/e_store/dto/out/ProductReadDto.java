package e_store.dto.out;

import java.math.BigDecimal;

public record ProductReadDto(
        Long id,
        String name,
        String description,
        BigDecimal price
) {
}
