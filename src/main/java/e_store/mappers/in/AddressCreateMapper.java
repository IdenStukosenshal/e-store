package e_store.mappers.in;

import e_store.database.entity.Address;
import e_store.dto.in.AddressCreateDto;
import e_store.mappers.MapperIntrf;
import org.springframework.stereotype.Component;

@Component
public class AddressCreateMapper implements MapperIntrf<AddressCreateDto, Address> {


    @Override
    public Address map(AddressCreateDto dto) {
        Address entity = new Address();

        entity.setCity(dto.city());
        entity.setPostalCode(dto.postalCode());
        entity.setStreetAddress(dto.streetAddress());
        return entity;
    }

}
