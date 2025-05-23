package e_store.unit_tests.mappers.out;

import e_store.database.entity.Address;
import e_store.dto.out.AddressReadDto;
import e_store.mappers.out.AddressReadMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressReadMapperTest {

    private final AddressReadMapper addressReadMapper = new AddressReadMapper();

    @Test
    void gettingEntityGeneratesDtoWithCorrectFields(){
        long id = 1L;
        String city = "City";
        String street = "Street Adress";
        Address entity = new Address(id, city, street);

        AddressReadDto dto = addressReadMapper.map(entity);

        assertEquals(city, dto.city());
        assertEquals(street, dto.streetAddress());
    }

}