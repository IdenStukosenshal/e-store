package e_store.mappers.in;

import e_store.database.entity.Address;
import e_store.database.entity.User;
import e_store.dto.in.AddressCreateUpdateDto;
import e_store.mappers.MapperIntrf;
import e_store.repositories.UserRepo;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class AddressCreateUpdateMapper implements MapperIntrf<AddressCreateUpdateDto, Address> {

    private final UserRepo userRepo;

    public AddressCreateUpdateMapper(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Address map(AddressCreateUpdateDto dto) {
        Address entity = new Address();
        entity.setUser(findUser(dto.userId()));
        copy(dto, entity);
        return entity;
    }

    @Override
    public Address mapUpd(AddressCreateUpdateDto dto, Address entity) {
        copy(dto, entity);
        return entity;
    }

    private void copy(AddressCreateUpdateDto dto, Address entity) {
        entity.setCity(dto.city());
        entity.setPostalCode(dto.postalCode());
        entity.setStreetAddress(dto.streetAddress());
    }


    private User findUser(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new ValidationException("not found, TEXT!1!!"));
    }
}
