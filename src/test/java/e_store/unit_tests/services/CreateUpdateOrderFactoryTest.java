package e_store.unit_tests.services;

import e_store.database.entity.*;
import e_store.dto.in.AddressCreateDto;
import e_store.dto.in.OrderCreateUpdateDto;
import e_store.enums.ErrorCode;
import e_store.enums.OrderStatus;
import e_store.exceptions.LocalizedValidationException;
import e_store.mappers.in.AddressCreateMapper;
import e_store.repositories.ProductRepo;
import e_store.repositories.UserRepo;
import e_store.services.CreateUpdateOrderFactory;
import e_store.services.GenerateOrderNumberService;
import e_store.services.OrderCostCalculationService;
import e_store.services.ValidateAddressService;
import jakarta.validation.ValidationException;
import org.instancio.Instancio;
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

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class CreateUpdateOrderFactoryTest {
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
    private CreateUpdateOrderFactory realCreateUpdateOrderFactory;

    private Set<Long> productsIdSet;
    private User user;
    private Address validatedAddress;
    private OrderCreateUpdateDto orderDto;
    private List<Product> productsLst;
    private List<OrderProduct> opList;


    @BeforeEach
    void setUp() {
        long productOneId = 2L;
        long productTwoId = 3L;
        int quantityOne = 10;
        int quantityTwo = 20;
        productsIdSet = Set.of(productOneId, productTwoId);

        long userId = 1L;
        user = new User();
        user.setId(userId);

        AddressCreateDto addressDto = new AddressCreateDto("City", "Street");
        validatedAddress = new Address(null, "ValidCity", "ValidStreet");

        Product productOne = new Product();
        productOne.setId(productOneId);
        productOne.setName("productName1");
        Product productTwo = new Product();
        productTwo.setId(productTwoId);
        productTwo.setName("productName2");
        productsLst = List.of(productOne, productTwo);

        OrderProduct orderProductOne = new OrderProduct();
        orderProductOne.setProduct(productOne);
        orderProductOne.setQuantity(quantityOne);
        OrderProduct orderProductTwo = new OrderProduct();
        orderProductTwo.setProduct(productTwo);
        orderProductTwo.setQuantity(quantityTwo);
        opList = List.of(orderProductOne, orderProductTwo);

        orderDto = new OrderCreateUpdateDto(
                userId,
                addressDto,
                Map.of(productOneId, quantityOne, productTwoId, quantityTwo)
        );
    }

    @Test
    void whenCreatingOrderWithValidDataThenOrderIsCreatedSuccessfully() {

        Mockito.when(mockUserRepo.findById(orderDto.userId())).thenReturn(Optional.of(user));
        Mockito.when(mockValidateAddressService.validAndCorrect(any())).thenReturn(validatedAddress);
        Mockito.when(mockProductRepo.findAllById(productsIdSet)).thenReturn(productsLst);
        Mockito.when(mockOrderCostCalculationService.calculate(anyList())).thenReturn(new BigDecimal("99.99"));
        Mockito.when(mockGenerateOrderNumberService.generate(any(Order.class))).thenReturn("orderNumber");

        Order resultOrder = realCreateUpdateOrderFactory.create(orderDto);

        assertNull(resultOrder.getId());
        assertEquals("orderNumber", resultOrder.getOrderNumber());
        assertEquals(user, resultOrder.getUser());
        assertEquals(OrderStatus.NEW, resultOrder.getStatus());
        assertEquals(new BigDecimal("99.99"), resultOrder.getOrderCost());
        assertEquals(validatedAddress, resultOrder.getAddress());
        assertEquals(opList, resultOrder.getOrderProductLst());
    }


    @Test
    void whenCreatingOrderWithNonExistingUserThenExceptionIsThrown() {

        Mockito.when(mockUserRepo.findById(anyLong())).thenReturn(Optional.empty());

        LocalizedValidationException exception = assertThrows(
                LocalizedValidationException.class,
                () -> realCreateUpdateOrderFactory.create(orderDto)
        );
        assertEquals(ErrorCode.NOT_FOUND.getMsg(), exception.getMsgCode());
        assertEquals("User", exception.getMsgArgs()[0]);
    }

    @Test
    void whenCreatingOrderWithInvalidAddressThenExceptionIsThrown() {

        Mockito.when(mockUserRepo.findById(anyLong())).thenReturn(Optional.of(new User()));
        Mockito.when(mockValidateAddressService.validAndCorrect(any())).thenThrow(new ValidationException("Address not found!"));

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> realCreateUpdateOrderFactory.create(orderDto)
        );
        assertEquals("Address not found!", exception.getMessage());
    }

    @Test
    void whenCreatingOrderWithInvalidProductsThenExceptionIsThrown() {

        Mockito.when(mockUserRepo.findById(anyLong())).thenReturn(Optional.of(new User()));
        Mockito.when(mockValidateAddressService.validAndCorrect(any())).thenReturn(new Address());
        Mockito.when(mockProductRepo.findAllById(anySet())).thenReturn(List.of());

        LocalizedValidationException exception = assertThrows(
                LocalizedValidationException.class,
                () -> realCreateUpdateOrderFactory.create(orderDto)
        );
        assertEquals(ErrorCode.NOT_FOUND.getMsg(), exception.getMsgCode());
        assertEquals("Products", exception.getMsgArgs()[0]);
    }

    @Test
    void whenUpdatingOrderWithValidDataThenOrderIsUpdatedSuccessfully() {
        Order origEntity = Instancio.of(Order.class)
                .set(field(Order::getStatus), OrderStatus.NEW)
                .create();

        Mockito.when(mockUserRepo.findById(orderDto.userId())).thenReturn(Optional.of(user));
        Mockito.when(mockValidateAddressService.validAndCorrect(any())).thenReturn(validatedAddress);
        Mockito.when(mockProductRepo.findAllById(productsIdSet)).thenReturn(productsLst);
        Mockito.when(mockOrderCostCalculationService.calculate(anyList())).thenReturn(new BigDecimal("99.99"));

        Order updatedEntity = realCreateUpdateOrderFactory.update(orderDto, origEntity);

        assertEquals(origEntity.getId(), updatedEntity.getId());
        assertEquals(origEntity.getStatus(), updatedEntity.getStatus());
        assertEquals(origEntity.getOrderNumber(), updatedEntity.getOrderNumber());
        assertEquals(new BigDecimal("99.99"), updatedEntity.getOrderCost());
        assertEquals(user, updatedEntity.getUser());
        assertEquals(validatedAddress, updatedEntity.getAddress());
        assertEquals(opList, updatedEntity.getOrderProductLst());
        assertEquals(origEntity.getCreatedAt(), updatedEntity.getCreatedAt());
    }
}

//TODO Не забыть потом пересмотреть и может быть сделать рефакторинг этих тестов