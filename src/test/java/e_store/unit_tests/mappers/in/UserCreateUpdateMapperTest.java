package e_store.unit_tests.mappers.in;

import e_store.database.entity.Order;
import e_store.database.entity.User;
import e_store.dto.in.UserCreateUpdateDto;
import e_store.mappers.in.UserCreateUpdateMapper;
import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserCreateUpdateMapperTest {

    private final UserCreateUpdateMapper userCreateUpdateMapper = new UserCreateUpdateMapper();
    private final UserCreateUpdateDto incDto = new UserCreateUpdateDto(
            "FirstName",
            "LastName",
            "email@a.com"
    );


    @Test
    void gettingUserDtoGeneratesEntityWithCorrectFields() {

        User entity = userCreateUpdateMapper.map(incDto);

        assertNull(entity.getId());
        assertTrue(entity.getOrdersLst().isEmpty());
        Assertions
                .assertThat(entity)
                .usingRecursiveComparison()
                .ignoringFields("id", "ordersLst")
                .isEqualTo(incDto);
    }

    @Test
    void gettingUserDtoCorrectlyUpdatesTheEntity() {
        List<Order> origOrderLst = Instancio.createList(Order.class);
        User origEntity = new User(
                1L,
                "Orig F Name",
                "Orig L Name",
                "orig@email.com",
                origOrderLst
        );

        User resultEntity = userCreateUpdateMapper.mapUpd(incDto, origEntity);

        assertEquals(origEntity.getId(), resultEntity.getId());
        assertEquals(origOrderLst, resultEntity.getOrdersLst());
        Assertions
                .assertThat(resultEntity)
                .usingRecursiveComparison()
                .ignoringFields("id", "ordersLst")
                .isEqualTo(incDto);
    }
}