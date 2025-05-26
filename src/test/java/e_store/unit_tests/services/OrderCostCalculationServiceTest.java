package e_store.unit_tests.services;

import e_store.database.entity.OrderProduct;
import e_store.database.entity.Product;
import e_store.services.OrderCostCalculationService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderCostCalculationServiceTest {

    private OrderCostCalculationService oCCService = new OrderCostCalculationService();

    @Test
    void checkCorrectCostCalculation(){
        BigDecimal priceOne = new BigDecimal("9.99");
        BigDecimal priceTwo = new BigDecimal("19.99");
        int quantityOne = 10;
        int quantityTwo = 20;

        Product productOne = new Product();
        productOne.setPrice(priceOne);

        OrderProduct orderProductOne = new OrderProduct();
        orderProductOne.setProduct(productOne);
        orderProductOne.setQuantity(quantityOne);

        Product productTwo = new Product();
        productTwo.setPrice(priceTwo);

        OrderProduct orderProductTwo = new OrderProduct();
        orderProductTwo.setProduct(productTwo);
        orderProductTwo.setQuantity(quantityTwo);

        BigDecimal result = oCCService.calculate(List.of(orderProductOne, orderProductTwo));

        assertEquals(new BigDecimal("499.70"), result);
    }

}