package e_store.integration_tests.database.entity;


import e_store.database.entity.*;
import e_store.enums.OrderStatus;
import e_store.repositories.*;
import jakarta.persistence.EntityManager;
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
class UserTest {

    private final UserRepo userRepo;
    private final AddressRepo addressRepo;
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final OrderProductRepo orderProductRepo;


    @Autowired
    public UserTest(UserRepo userRepo,
                    AddressRepo addressRepo,
                    OrderRepo orderRepo,
                    ProductRepo productRepo,
                    OrderProductRepo orderProductRepo) {
        this.userRepo = userRepo;
        this.addressRepo = addressRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.orderProductRepo = orderProductRepo;
    }

    @Test
    public void deletingUserCausesCorrectCascadingDeletion(){
        User userOne = new User();
        userOne.setFirstName("FirstName_1");
        userOne.setLastName("LastName_1");
        userOne.setEmail("first@email.com_1");
        User savedUser = userRepo.save(userOne);

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
        orderOne.setOrderNumber("n1");
        orderOne.setStatus(OrderStatus.NEW);
        orderOne.setOrderCost(BigDecimal.valueOf(100));
        orderOne.setAddress(address);
        orderOne.addProduct(productOne, 10);

        Order orderTwo = new Order();
        orderTwo.setUser(userOne);
        orderTwo.setOrderNumber("n2");
        orderTwo.setStatus(OrderStatus.NEW);
        orderTwo.setOrderCost(BigDecimal.valueOf(200));
        orderTwo.setAddress(address);
        orderTwo.addProduct(productTwo, 20);

        orderRepo.save(orderOne);
        orderRepo.save(orderTwo);

        //Act
        userRepo.delete(userOne);

        //Assert
        assertFalse(userRepo.existsById(savedUser.getId()));
        assertTrue(orderProductRepo.findAllByUserId(savedUser.getId()).isEmpty());
        assertTrue(orderRepo.findAll().isEmpty());
        assertFalse(addressRepo.findAll().isEmpty());
        assertFalse(productRepo.findAll().isEmpty());
    }
}