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
public class CommonEntityTest {

    private final AddressRepo addressRepo;
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    @Autowired
    public CommonEntityTest(AddressRepo addressRepo,
                            OrderRepo orderRepo,
                            ProductRepo productRepo,
                            UserRepo userRepo) {
        this.addressRepo = addressRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    @Test
    public void checkSaveFindEntities() {
        //Arrange
        User userOne = new User();
        userOne.setFirstName("FirstName_1");
        userOne.setLastName("LastName_1");
        userOne.setEmail("first@email.com_1");
        userRepo.save(userOne);

        Address addressOne = new Address();
        addressOne.setCity("CityName_1");
        addressOne.setPostalCode("post-123");
        addressOne.setStreetAddress("StreetAddress_1");
        addressOne.setUser(userOne);
        addressRepo.save(addressOne);

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
        orderOne.setAddress(addressOne);

        orderOne.addProduct(productOne, 10);
        orderOne.addProduct(productTwo, 20);


        //Act
        Order savedOrder = orderRepo.save(orderOne);
        Order mayBeOrder = orderRepo.findById(savedOrder.getId()).orElseGet(Order::new);

        //Assert
        assertEquals(orderOne, mayBeOrder); //Equals: Order, Address, User
        assertEquals(orderOne.getOrderProductLst(), mayBeOrder.getOrderProductLst()); //Equals: OrderProduct, Product
    }
}
