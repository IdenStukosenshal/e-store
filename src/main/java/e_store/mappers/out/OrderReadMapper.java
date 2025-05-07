package e_store.mappers.out;

import e_store.database.entity.Order;
import e_store.dto.out.OrderReadDto;
import e_store.mappers.MapperIntrf;
import org.springframework.stereotype.Component;

@Component
public class OrderReadMapper implements MapperIntrf<Order, OrderReadDto> {
    private final AddressReadMapper addressReadMapper;
    private final OrderProductReadMapper orderProductReadMapper;

    public OrderReadMapper(AddressReadMapper addressReadMapper,
                           OrderProductReadMapper orderProductReadMapper) {
        this.addressReadMapper = addressReadMapper;
        this.orderProductReadMapper = orderProductReadMapper;
    }


    @Override
    public OrderReadDto map(Order obj) {
        return new OrderReadDto(
                obj.getId(),
                obj.getOrderNumber(),
                obj.getUser().getId(),
                obj.getStatus(),
                obj.getOrderCost(),
                obj.getCreatedAt(),
                obj.getUpdatedAt(),
                obj.getDeliveryDate(),
                addressReadMapper.map(obj.getAddress()),
                obj.getOrderProductLst().stream().map(orderProductReadMapper::map).toList()
        );
    }
}
