package e_store.services;

import e_store.dto.in.OrderCreateUpdateDto;
import e_store.dto.out.OrderReadDto;
import e_store.mappers.in.OrderCreateUpdateMapper;
import e_store.mappers.out.OrderReadMapper;
import e_store.repositories.OrderRepo;
import jakarta.validation.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final OrderReadMapper orderReadMapper;
    private final OrderCreateUpdateMapper orderCreateUpdateMapper;

    public OrderService(OrderRepo orderRepo,
                        OrderReadMapper orderReadMapper,
                        OrderCreateUpdateMapper orderCreateUpdateMapper) {
        this.orderRepo = orderRepo;
        this.orderReadMapper = orderReadMapper;
        this.orderCreateUpdateMapper = orderCreateUpdateMapper;
    }

    @Transactional(readOnly = true)
    public Page<OrderReadDto> findAll(PageRequest pageRequest) {
        return orderRepo
                .findAll(pageRequest)
                .map(orderReadMapper::map);
    }

    @Transactional(readOnly = true)
    public OrderReadDto findById(Long id) {
        var entity = orderRepo.findById(id).orElseThrow(() -> new ValidationException("not found, TEXT!1!!"));
        return orderReadMapper.map(entity);
    }

    public OrderReadDto create(OrderCreateUpdateDto dto) {
        var entity = orderCreateUpdateMapper.map(dto);
        var savedEntity = orderRepo.save(entity);
        return orderReadMapper.map(savedEntity);
    }

    public OrderReadDto update(Long id, OrderCreateUpdateDto updateDto) {
        var entity = orderRepo.findById(id).orElseThrow(() -> new ValidationException("not found, TEXT!1!!"));
        var updatedEntity = orderCreateUpdateMapper.mapUpd(updateDto, entity);
        var savedEntity = orderRepo.saveAndFlush(updatedEntity);
        return orderReadMapper.map(savedEntity);
    }

    public void deleteById(Long id) {
        if (orderRepo.existsById(id)) {
            orderRepo.deleteById(id);
            orderRepo.flush();
        } else {
            throw new ValidationException("not found, TEXT!1!!");
        }
    }

    public void deleteAll() {
        orderRepo.deleteAll();
    }
}
