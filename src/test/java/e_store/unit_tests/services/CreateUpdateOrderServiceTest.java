package e_store.unit_tests.services;

import e_store.database.entity.*;
import e_store.dto.in.AddressCreateDto;
import e_store.dto.in.OrderCreateUpdateDto;
import e_store.enums.OrderStatus;
import e_store.mappers.in.AddressCreateMapper;
import e_store.repositories.ProductRepo;
import e_store.repositories.UserRepo;
import e_store.services.CreateUpdateOrderService;
import e_store.services.GenerateOrderNumberService;
import e_store.services.OrderCostCalculationService;
import e_store.services.ValidateAddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;

@ExtendWith(MockitoExtension.class)
class CreateUpdateOrderServiceTest {
    @Mock
    private ValidateAddressService mockValidateAddressService;
    @Mock
    private UserRepo mockUserRepo;
    @Mock
    private AddressCreateMapper mockAddressCreateMapper;
    @Mock
    private ProductRepo mockProductRepo;
    @Mock
    private OrderCostCalculationService mockOrderCostCalculationService;
    @Mock
    private GenerateOrderNumberService mockGenerateOrderNumberService;

    @InjectMocks
    private CreateUpdateOrderService realCreateUpdateOrderService;

    /*
        whenCreatingOrderWithValidDataThenOrderIsCreatedSuccessfully
    whenCreatingOrderWithNonExistingUserThenExceptionIsThrown
    whenCreatingOrderWithInvalidAddressThenExceptionIsThrown
    whenCreatingOrderWithInvalidProductsThenExceptionIsThrown

    whenUpdatingOrderWithValidDataThenOrderIsUpdatedSuccessfully
    whenUpdatingOrderWithNewProductMapThenProductListIsCleared
    whenUpdatingOrderWithEmptyProductMapThenProductListRemainsUnchanged


    Mockito.when(mockAddressReadMapper.map(address)).thenReturn(addressReadDto);
    when(generateOrderNumberService.generate(any(Order.class))).thenReturn("ORD-001");
    when(orderCostCalculationService.calculate(anyList())).thenReturn(new BigDecimal("25.00"));
    Mockito.verify(mockAddressReadMapper).map(address);

     */

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenCreatingOrderWithValidDataThenOrderIsCreatedSuccessfully(){
        long userId = 1L;
        long productOneId = 2L, productTwoId = 3L;
        int quantityOne = 10, quantityTwo = 20;
        Set<Long> productsIdSet = Set.of(productOneId, productTwoId);

        User user = new User();
        user.setId(userId);

        AddressCreateDto addressDto = new AddressCreateDto("City", "Street");
        Address validatedAddress = new Address(null, "ValidCity", "ValidStreet");

        Product productOne = new Product();
        productOne.setId(productOneId);
        productOne.setName("productName1");
        Product productTwo = new Product();
        productTwo.setId(productTwoId);
        productTwo.setName("productName2");
        List<Product> productsLst = List.of(productOne, productTwo);

        OrderProduct orderProductOne = new OrderProduct();
        orderProductOne.setProduct(productOne);
        orderProductOne.setQuantity(quantityOne);
        OrderProduct orderProductTwo = new OrderProduct();
        orderProductTwo.setProduct(productTwo);
        orderProductTwo.setQuantity(quantityTwo);
        List<OrderProduct> opList = List.of(orderProductOne, orderProductTwo);

        OrderCreateUpdateDto orderDto = new OrderCreateUpdateDto(
                userId,
                addressDto,
                Map.of(productOneId, quantityOne, productTwoId, quantityTwo)
        );

        Mockito.when(mockUserRepo.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(mockAddressCreateMapper.map(addressDto)).thenReturn(new Address());
        Mockito.when(mockValidateAddressService.validAndCorrect(any(Address.class))).thenReturn(validatedAddress);
        Mockito.when(mockProductRepo.findAllById(productsIdSet)).thenReturn(productsLst);
        Mockito.when(mockOrderCostCalculationService.calculate(anyList())).thenReturn(new BigDecimal("99.99"));
        Mockito.when(mockGenerateOrderNumberService.generate(any(Order.class))).thenReturn("orderNumber");

        //Act
        Order resultOrder = realCreateUpdateOrderService.create(orderDto);

        //Assert
        assertEquals("orderNumber", resultOrder.getOrderNumber());
        assertEquals(user, resultOrder.getUser());
        assertEquals(OrderStatus.NEW, resultOrder.getStatus());
        assertEquals(new BigDecimal("99.99"), resultOrder.getOrderCost());
        assertEquals(validatedAddress, resultOrder.getAddress());
        assertEquals(opList, resultOrder.getOrderProductLst());
    }
}