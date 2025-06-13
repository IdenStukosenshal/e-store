package e_store.unit_tests.mappers.in;

import e_store.database.entity.Address;
import e_store.dto.in.AddressCreateDto;
import e_store.mappers.in.AddressCreateMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class AddressCreateMapperTest {

    private final AddressCreateMapper addressCreateMapper = new AddressCreateMapper();
    private final AddressCreateDto incDto = new AddressCreateDto(
            "City",
            "Street Adress");

    @Test
    void gettingAddressDtoGeneratesEntityWithCorrectFields() {

        Address entity = addressCreateMapper.map(incDto);

        assertNull(entity.getId());
        Assertions
                .assertThat(entity)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(incDto);
    }
}