package e_store.integration_tests.database.entity;

import e_store.database.entity.*;
import e_store.enums.OrderStatus;
import e_store.repositories.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class CommonTestOne {

    private final AddressRepo addressRepo;
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    @Autowired
    public CommonTestOne(AddressRepo addressRepo,
                         OrderRepo orderRepo,
                         ProductRepo productRepo,
                         UserRepo userRepo) {
        this.addressRepo = addressRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    @Test
    public void simpleCheck() {
        //Arrange
        User userOne = new User();
        userOne.setFirstName("FirstName_1");
        userOne.setLastName("LastName_1");
        userOne.setEmail("first@email.com_1");
        userRepo.save(userOne);

        Address address = new Address();
        address.setCity("CityName_1");
        address.setPostalCode("post-123");
        address.setStreetAddress("StreetAddress_1");
        addressRepo.save(address);

        Product productOne = new Product();
        productOne.setName("_1");
        productOne.setDescription("_1");
        productOne.setPrice(BigDecimal.valueOf(10));

        Product productTwo = new Product();
        productTwo.setName("_2");
        productTwo.setDescription("_2");
        productTwo.setPrice(BigDecimal.valueOf(20));

        List<Product> productsLst = List.of(productOne, productTwo);
        productRepo.saveAll(productsLst);

        Order orderOne = new Order();
        orderOne.setUser(userOne);
        orderOne.setOrderNumber("4521734");
        orderOne.setStatus(OrderStatus.NEW);
        orderOne.setOrderCost(productsLst.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        orderOne.setAddress(address);

        OrderProduct orderProductOne = new OrderProduct();
        orderProductOne.setOrder(orderOne);
        orderProductOne.setProduct(productOne);
        orderProductOne.setQuantity(10L);

        OrderProduct orderProductTwo = new OrderProduct();
        orderProductTwo.setOrder(orderOne);
        orderProductTwo.setProduct(productTwo);
        orderProductTwo.setQuantity(20L);

        //Act
        Order savedOrder = orderRepo.save(orderOne);
        Order mb = orderRepo.findById(savedOrder.getId()).orElseGet(Order::new);

        //Assert
        assertEquals(orderOne, mb);
        assertEquals(orderOne.getOrderProductLst(), mb.getOrderProductLst());

    }
}
