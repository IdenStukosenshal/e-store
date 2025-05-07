package e_store.mappers.out;

import e_store.database.entity.User;
import e_store.dto.out.UserReadDto;
import e_store.mappers.MapperIntrf;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements MapperIntrf<User, UserReadDto> {

    private final OrderReadMapper orderReadMapper;

    public UserReadMapper(OrderReadMapper orderReadMapper) {
        this.orderReadMapper = orderReadMapper;
    }

    @Override
    public UserReadDto map(User obj) {
        return new UserReadDto(
                obj.getId(),
                obj.getFirstName(),
                obj.getLastName(),
                obj.getEmail(),
                obj.getOrdersLst().stream().map(orderReadMapper::map).toList());
    }
}
