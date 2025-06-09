package e_store.unit_tests.mappers.out;

import e_store.database.entity.*;
import e_store.dto.out.AddressReadDto;
import e_store.dto.out.OrderProductReadDto;
import e_store.dto.out.OrderReadDto;
import e_store.dto.out.ProductReadDto;
import e_store.enums.OrderStatus;
import e_store.mappers.out.AddressReadMapper;
import e_store.mappers.out.OrderProductReadMapper;
import e_store.mappers.out.OrderReadMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderReadMapperTest {
    private AddressReadMapper mockAddressReadMapper;
    private OrderProductReadMapper mockOrderProductReadMapper;
    private OrderReadMapper realOrderReadMapper;

    @BeforeEach
    void setUp() {
        this.mockAddressReadMapper = Mockito.mock(AddressReadMapper.class);
        this.mockOrderProductReadMapper = Mockito.mock(OrderProductReadMapper.class);
        this.realOrderReadMapper = new OrderReadMapper(mockAddressReadMapper, mockOrderProductReadMapper);
    }

    @Test
    void gettingOrderEntityGeneratesDtoWithCorrectFields() {
        String city = "CityName";
        String street = "Street Address";
        int quantity = 10;

        User user = new User();
        user.setId(1L);
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setEmail("email@email.com");

        Address address = new Address();
        address.setCity(city);
        address.setStreetAddress(street);

        Product productOne = new Product();
        productOne.setId(2L);
        productOne.setPrice(BigDecimal.valueOf(9.99));
        productOne.setName("ProductName");
        productOne.setDescription("Description");

        Order order = new Order();
        order.setId(3L);
        order.setOrderNumber("1234");
        order.setUser(user);
        order.setStatus(OrderStatus.PROCESSING);
        order.setAddress(address);
        order.setOrderCost(productOne.getPrice().multiply(BigDecimal.valueOf(quantity)));

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setId(4L);
        orderProduct.setProduct(productOne);
        orderProduct.setOrder(order);
        orderProduct.setQuantity(quantity);

        order.setOrderProductLst(List.of(orderProduct));

        AddressReadDto addressReadDto = new AddressReadDto(address.getCity(), address.getStreetAddress());
        OrderProductReadDto orderProductReadDto = new OrderProductReadDto(new ProductReadDto(
                productOne.getId(),
                productOne.getName(),
                productOne.getDescription(),
                productOne.getPrice()), orderProduct.getQuantity());

        Mockito.when(mockAddressReadMapper.map(address)).thenReturn(addressReadDto);
        Mockito.when(mockOrderProductReadMapper.map(orderProduct)).thenReturn(orderProductReadDto);

        //Act
        OrderReadDto resultDto = realOrderReadMapper.map(order);

        //Assert
        assertEquals(order.getId(), resultDto.id());
        assertEquals(order.getOrderNumber(), resultDto.orderNumber());
        assertEquals(order.getUser().getId(), resultDto.userId());
        assertEquals(order.getStatus(), resultDto.status());
        assertEquals(order.getOrderCost(), resultDto.orderCost());
        assertEquals(order.getCreatedAt(), resultDto.createdAt());
        assertEquals(order.getUpdatedAt(), resultDto.updatedAt());
        assertEquals(addressReadDto, resultDto.addressDto());
        assertEquals(List.of(orderProductReadDto), resultDto.orderProductDtoLst());
    }
}