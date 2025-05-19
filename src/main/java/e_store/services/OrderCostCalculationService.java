package e_store.services;

import e_store.database.entity.OrderProduct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderCostCalculationService {

    public BigDecimal calculate(List<OrderProduct> orderProductLst) {
        BigDecimal summ = BigDecimal.ZERO;
        for (OrderProduct op : orderProductLst) {
            summ = summ.add(op.getProduct().getPrice().multiply(BigDecimal.valueOf(op.getQuantity())));
        }
        return summ;
    }
}
