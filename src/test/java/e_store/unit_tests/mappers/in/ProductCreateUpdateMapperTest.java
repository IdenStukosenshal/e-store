package e_store.unit_tests.mappers.in;

import e_store.database.entity.Product;
import e_store.dto.in.ProductCreateUpdateDto;
import e_store.mappers.in.ProductCreateUpdateMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductCreateUpdateMapperTest {

    private final ProductCreateUpdateMapper productCreateUpdateMapper = new ProductCreateUpdateMapper();
    private final ProductCreateUpdateDto incDto = new ProductCreateUpdateDto(
            "Name",
            "Description",
            new BigDecimal("999.99"));


    @Test
    void gettingProductDtoGeneratesEntityWithCorrectFields() {

        Product entity = productCreateUpdateMapper.map(incDto);

        Assertions
                .assertThat(entity)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(incDto);
        assertNull(entity.getId());
    }

    @Test
    void gettingProductDtoCorrectlyUpdatesTheEntity() {
        Product origEntity = new Product(
                1L,
                "Original Name",
                "Original Description",
                new BigDecimal("0")
        );

        Product resultEntity = productCreateUpdateMapper.mapUpd(incDto, origEntity);

        assertEquals(origEntity.getId(), resultEntity.getId());
        Assertions
                .assertThat(resultEntity)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(incDto);
    }
}