package e_store.unit_tests.mappers.out;

import e_store.database.entity.Product;
import e_store.dto.out.ProductReadDto;
import e_store.mappers.out.ProductReadMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ProductReadMapperTest {

    private final ProductReadMapper productReadMapper = new ProductReadMapper();

    @Test
    void gettingProductEntityGeneratesDtoWithCorrectFields() {
        Product entity = new Product(
                1L,
                "Name",
                "Description",
                new BigDecimal("12.99"));

        ProductReadDto dto = productReadMapper.map(entity);

        Assertions
                .assertThat(entity)
                .usingRecursiveComparison()
                .isEqualTo(dto);
    }
}