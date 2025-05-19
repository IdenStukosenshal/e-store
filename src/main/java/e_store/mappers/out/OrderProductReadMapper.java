package e_store.mappers.out;

import e_store.database.entity.OrderProduct;
import e_store.dto.out.OrderProductReadDto;
import e_store.mappers.MapperIntrf;
import org.springframework.stereotype.Component;

@Component
public class OrderProductReadMapper implements MapperIntrf<OrderProduct, OrderProductReadDto> {

    private final ProductReadMapper productReadMapper;

    public OrderProductReadMapper(ProductReadMapper productReadMapper) {
        this.productReadMapper = productReadMapper;
    }

    @Override
    public OrderProductReadDto map(OrderProduct obj) {
        return new OrderProductReadDto(
                productReadMapper.map(obj.getProduct()),
                obj.getQuantity());
    }
}
