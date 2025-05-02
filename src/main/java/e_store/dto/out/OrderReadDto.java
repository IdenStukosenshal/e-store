package e_store.dto.out;

import e_store.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public record OrderReadDto(
        Long id,
        String orderNumber,
        Long userId,
        OrderStatus status,
        BigDecimal orderCost,
        Instant createdAt, //TODO ??
        Instant updatedAt, //TODO ??
        LocalDateTime deliveryDate,
        AddressReadDto addressDto,
        List<OrderProductReadDto> orderProductDtoLst
) {
}
