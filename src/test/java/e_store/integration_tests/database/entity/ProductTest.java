package e_store.integration_tests.database.entity;

import e_store.database.entity.Product;
import e_store.repositories.ProductRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class ProductTest {

    @Autowired
    private ProductRepo productRepo;

    @Test
    public void saveFindEntityCheck(){
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