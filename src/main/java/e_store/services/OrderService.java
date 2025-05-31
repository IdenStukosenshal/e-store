package e_store.services;

import e_store.database.entity.Order;
import e_store.dto.in.OrderCreateUpdateDto;
import e_store.dto.out.OrderReadDto;
import e_store.enums.ErrorCode;
import e_store.enums.OrderStatus;
import e_store.exceptions.LocalizedValidationException;
import e_store.mappers.out.OrderReadMapper;
import e_store.repositories.OrderRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final OrderReadMapper orderReadMapper;
    private final CreateUpdateOrderService createUpdateOrderService;

    public OrderService(OrderRepo orderRepo,
                        OrderReadMapper orderReadMapper,
                        CreateUpdateOrderService createUpdateOrderService) {
        this.orderRepo = orderRepo;
        this.orderReadMapper = orderReadMapper;
        this.createUpdateOrderService = createUpdateOrderService;
    }

    @Transactional(readOnly = true)
    public Page<OrderReadDto> findAll(PageRequest pageRequest) {
        return orderRepo
                .findAll(pageRequest)
                .map(orderReadMapper::map);
    }

    @Transactional(readOnly = true)
    public OrderReadDto findById(Long id) {
        Order entity = orderRepo.findById(id).orElseThrow(() -> new LocalizedValidationException(ErrorCode.NOT_FOUND.getMsg(), "Order"));
        return orderReadMapper.map(entity);
    }

    public OrderReadDto create(OrderCreateUpdateDto dto) {
        Order savedEntity = orderRepo.save(createUpdateOrderService.create(dto));
        return orderReadMapper.map(savedEntity);
    }

    public OrderReadDto update(Long id, OrderCreateUpdateDto updateDto) {
        Order entity = orderRepo.findById(id).orElseThrow(() -> new LocalizedValidationException(ErrorCode.NOT_FOUND.getMsg(), "Order"));
        //1
        if (!entity.getStatus().equals(OrderStatus.NEW))
            throw new LocalizedValidationException(ErrorCode.TOO_LATE.getMsg());

        Order updatedEntity = createUpdateOrderService.update(updateDto, entity);
        Order savedEntity = orderRepo.save(updatedEntity);
        return orderReadMapper.map(savedEntity);
    }

    public void deleteById(Long id) {
        if (orderRepo.existsById(id)) {
            orderRepo.deleteById(id);
            orderRepo.flush();
        } else {
            throw new LocalizedValidationException(ErrorCode.NOT_FOUND.getMsg(), "Order");
        }
    }


}


/*1 Идея:
предполагается, что пользователь может изменить заказ пока его статус "NEW".
После - только отменить.
А статус меняется в другом месте, например сотрудником магазина вручную.

Одна из альтернатив - запрет изменения заказа, только отмена
*/