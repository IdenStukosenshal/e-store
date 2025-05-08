package e_store.rest;


import e_store.dto.in.OrderCreateUpdateDto;
import e_store.dto.out.OrderReadDto;
import e_store.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderReadDto> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderReadDto findById(@PathVariable("id") Long id) {
        return orderService.findById(id);
    }

    @PostMapping
    public OrderReadDto create(@Valid @RequestBody OrderCreateUpdateDto dto) {
        return orderService.create(dto);
    }

    @PutMapping("/{id}")
    public OrderReadDto update(@PathVariable("id") Long id, @Valid @RequestBody OrderCreateUpdateDto updateDto) {
        return orderService.update(id, updateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        orderService.deleteById(id);
    }

    @DeleteMapping
    public void deleteAll() {
        orderService.deleteAll();
    }
}
