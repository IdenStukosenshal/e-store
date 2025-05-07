package e_store.mappers.in;

import e_store.database.entity.Product;
import e_store.dto.in.ProductCreateUpdateDto;
import e_store.mappers.MapperIntrf;
import org.springframework.stereotype.Component;

@Component
public class ProductCreateUpdateMapper implements MapperIntrf<ProductCreateUpdateDto, Product> {

    @Override
    public Product map(ProductCreateUpdateDto dto) {
        Product entity = new Product();
        copy(dto, entity);
        return entity;
    }

    @Override
    public Product mapUpd(ProductCreateUpdateDto dto, Product entity) {
        copy(dto, entity);
        return entity;
    }

    private void copy(ProductCreateUpdateDto dto, Product entity) {
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setPrice(dto.price());
    }
}
