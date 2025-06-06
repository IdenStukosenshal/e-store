package e_store.rest;


import e_store.dto.filter.OrderFilter;
import e_store.dto.in.OrderCreateUpdateDto;
import e_store.dto.out.OrderReadDto;
import e_store.dto.out.PageResponseDto;
import e_store.services.OrderService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public PageResponseDto<OrderReadDto> findAll(
            @ParameterObject OrderFilter orderFilter,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size
    ) {
        Page<OrderReadDto> pagedData = orderService.findAll(orderFilter, PageRequest.of(page, size));
        return PageResponseDto.of(pagedData);
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

}
