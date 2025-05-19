package e_store.dto.out;

import e_store.database.entity.Order;
import e_store.database.entity.Product;

public record OrderProductReadDto(
        ProductReadDto productDto,
        Integer quantity
) {
}
