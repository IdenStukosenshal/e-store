package e_store.services;

import com.querydsl.core.types.Predicate;
import e_store.database.entity.Order;
import e_store.database.entity.QOrder;
import e_store.dto.filter.OrderFilter;
import e_store.dto.in.OrderCreateUpdateDto;
import e_store.dto.out.OrderReadDto;
import e_store.enums.ErrorCode;
import e_store.enums.OrderStatus;
import e_store.exceptions.LocalizedValidationException;
import e_store.mappers.out.OrderReadMapper;
import e_store.repositories.OrderRepo;
import e_store.utils.QPredicatesBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final OrderReadMapper orderReadMapper;
    private final CreateUpdateOrderFactory createUpdateOrderFactory;

    public OrderService(OrderRepo orderRepo,
                        OrderReadMapper orderReadMapper,
                        CreateUpdateOrderFactory createUpdateOrderFactory) {
        this.orderRepo = orderRepo;
        this.orderReadMapper = orderReadMapper;
        this.createUpdateOrderFactory = createUpdateOrderFactory;
    }

    @Transactional(readOnly = true)
    public Page<OrderReadDto> findAll(OrderFilter orderFilter, PageRequest pageRequest) {
        Predicate predicate = buildPredicate(orderFilter);
        return orderRepo
                .findAll(predicate, pageRequest)
                .map(orderReadMapper::map);
    }

    @Transactional(readOnly = true)
    public OrderReadDto findById(Long id) {
        Order entity = orderRepo.findById(id).orElseThrow(() -> new LocalizedValidationException(ErrorCode.NOT_FOUND.getMsg(), "Order"));
        return orderReadMapper.map(entity);
    }

    public OrderReadDto create(OrderCreateUpdateDto dto) {
        Order savedEntity = orderRepo.save(createUpdateOrderFactory.create(dto));
        return orderReadMapper.map(savedEntity);
    }

    public OrderReadDto update(Long id, OrderCreateUpdateDto updateDto) {
        Order entity = orderRepo.findById(id).orElseThrow(() -> new LocalizedValidationException(ErrorCode.NOT_FOUND.getMsg(), "Order"));
        //1
        if (!entity.getStatus().equals(OrderStatus.NEW))
            throw new LocalizedValidationException(ErrorCode.TOO_LATE.getMsg());

        Order updatedEntity = createUpdateOrderFactory.update(updateDto, entity);
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

    private Predicate buildPredicate(OrderFilter orderFilter) {

        QOrder order = QOrder.order;

        return QPredicatesBuilder.of()
                .add(orderFilter.orderNumber(), order.orderNumber::eq)
                .add(orderFilter.productName(), order.orderProductLst.any().product.name::containsIgnoreCase)
                .buildAnd();
    }
}


/*1 Идея:
предполагается, что пользователь может изменить заказ пока его статус "NEW".
После - только отменить.
А статус меняется в другом месте, например сотрудником магазина вручную.

Одна из альтернатив - запрет изменения заказа, только отмена
*/