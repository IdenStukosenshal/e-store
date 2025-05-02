package e_store.dto.out;

import e_store.database.entity.Order;
import e_store.database.entity.Product;

public record OrderProductReadDto(
        Long id,
        ProductReadDto productDto,
        Long quantity
) {
}
