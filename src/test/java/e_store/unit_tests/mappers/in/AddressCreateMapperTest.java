package e_store.unit_tests.mappers.in;

import e_store.database.entity.Address;
import e_store.dto.in.AddressCreateDto;
import e_store.mappers.in.AddressCreateMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressCreateMapperTest {

    private final AddressCreateMapper addressCreateMapper = new AddressCreateMapper();

    @Test
    void gettingDtoGeneratesEntityWithCorrectFields(){
        String city = "City";
        String street = "Street Adress";
        AddressCreateDto dto = new AddressCreateDto(city, street);

        Address entity = addressCreateMapper.map(dto);

        assertEquals(city, entity.getCity());
        assertEquals(street, entity.getStreetAddress());
        assertNull(entity.getId());
    }

}