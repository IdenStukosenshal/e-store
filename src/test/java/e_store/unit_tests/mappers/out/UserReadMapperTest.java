package e_store.unit_tests.mappers.out;

import e_store.database.entity.Order;
import e_store.database.entity.User;
import e_store.dto.out.OrderReadDto;
import e_store.dto.out.UserReadDto;
import e_store.mappers.out.OrderReadMapper;
import e_store.mappers.out.UserReadMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserReadMapperTest {

    private OrderReadMapper mockOrderReadMapper;
    private UserReadMapper realUserReadMapper;

    @BeforeEach
    void setUp() {
        this.mockOrderReadMapper = Mockito.mock(OrderReadMapper.class);
        this.realUserReadMapper = new UserReadMapper(mockOrderReadMapper);
    }

    @Test
    void gettingUserEntityGeneratesDtoWithCorrectFields() {
        Order order = new Order();
        User entity = new User(
                1L,
                "FName",
                "LName",
                "email@a.com",
                List.of(order));
        OrderReadDto orderReadDto = Instancio.create(OrderReadDto.class);
        Mockito.when(mockOrderReadMapper.map(order)).thenReturn(orderReadDto);

        UserReadDto dto = realUserReadMapper.map(entity);

        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getFirstName(), dto.firstName());
        assertEquals(entity.getLastName(), dto.lastName());
        assertEquals(entity.getEmail(), dto.email());
        assertEquals(entity.getOrdersLst().size(), dto.ordersDtoLst().size());
    }
}

/*
        AddressCreateDto addressDto = Instancio.create(AddressCreateDto.class);

        AddressCreateDto addressDto = Instancio.of(AddressCreateDto.class)
                .set(field(AddressCreateDto::city), "city")
                .create();

*/