package e_store.mappers.in;

import e_store.database.entity.Address;
import e_store.dto.in.AddressCreateUpdateDto;
import e_store.mappers.MapperIntrf;

public class AddressCreateUpdateMapper implements MapperIntrf<AddressCreateUpdateDto, Address> {
    @Override
    public Address map(AddressCreateUpdateDto dto) {
        var entity = new Address();
        copy(dto, entity);
        return entity;
    }


    @Override
    public Address mapUpd(AddressCreateUpdateDto dto, Address entity){
        copy(dto, entity);
        return  entity;
    }

    private void copy(AddressCreateUpdateDto dto, Address entity){
        entity.setCity(dto.city());
        entity.setPostalCode(dto.postalCode());
        entity.setStreetAddress(dto.streetAddress());
    }
}
