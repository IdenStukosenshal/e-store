package e_store.mappers.out;

import e_store.database.entity.Product;
import e_store.dto.out.ProductReadDto;
import e_store.mappers.MapperIntrf;
import org.springframework.stereotype.Component;

@Component
public class ProductReadMapper implements MapperIntrf<Product, ProductReadDto> {
    @Override
    public ProductReadDto map(Product obj) {
        return new ProductReadDto(
                obj.getId(),
                obj.getName(),
                obj.getDescription(),
                obj.getPrice());
    }
}
