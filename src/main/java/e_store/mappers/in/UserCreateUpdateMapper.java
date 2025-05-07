package e_store.mappers.in;

import e_store.database.entity.User;
import e_store.dto.in.UserCreateUpdateDto;
import e_store.mappers.MapperIntrf;
import org.springframework.stereotype.Component;

@Component
public class UserCreateUpdateMapper implements MapperIntrf<UserCreateUpdateDto, User> {

    @Override
    public User map(UserCreateUpdateDto dto) {
        User entity = new User();
        copy(dto, entity);
        return entity;

    }

    @Override
    public User mapUpd(UserCreateUpdateDto dto, User entity) {
        copy(dto, entity);
        return entity;
    }

    private void copy(UserCreateUpdateDto dto, User entity) {
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setEmail(dto.email());
    }
}
