package e_store.rest;

import e_store.dto.filter.ProductFilter;
import e_store.dto.in.ProductCreateUpdateDto;
import e_store.dto.out.PageResponseDto;
import e_store.dto.out.ProductReadDto;
import e_store.services.ProductService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject; //swagger
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public PageResponseDto<ProductReadDto> findAll(
            @ParameterObject ProductFilter productFilter,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size
    ) {
        Page<ProductReadDto> pagedData = productService.findAll(productFilter, PageRequest.of(page, size));
        return PageResponseDto.of(pagedData);
    }

    @GetMapping("/{id}")
    public ProductReadDto findById(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    @PostMapping
    public ProductReadDto create(@Valid @RequestBody ProductCreateUpdateDto dto) {
        return productService.create(dto);
    }

    @PutMapping("/{id}")
    public ProductReadDto update(@PathVariable("id") Long id, @Valid @RequestBody ProductCreateUpdateDto updateDto) {
        return productService.update(id, updateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        productService.deleteById(id);
    }

}
