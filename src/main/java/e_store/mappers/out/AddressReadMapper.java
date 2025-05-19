package e_store.mappers.out;

import e_store.database.entity.Address;
import e_store.dto.out.AddressReadDto;
import e_store.mappers.MapperIntrf;
import org.springframework.stereotype.Component;

@Component
public class AddressReadMapper implements MapperIntrf<Address, AddressReadDto> {
    @Override
    public AddressReadDto map(Address obj) {
        return new AddressReadDto(
                obj.getCity(),
                obj.getPostalCode(),
                obj.getStreetAddress());
    }
}
