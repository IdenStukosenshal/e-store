package e_store.database.entity;

import e_store.repositories.ProductRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ProductTest {

    @Autowired
    private ProductRepo productRepo;

    @Test
    public void saveEntity(){
        String productName = "product_1";

        var product = new Product();
        product.setName(productName);
        product.setDescription("description_1");
        product.setPrice(BigDecimal.valueOf(100));

        productRepo.save(product);

        Product mb = productRepo.findByName(productName).orElseGet(Product::new);
        assertEquals(product, mb);

    }

}