package e_store.rest;

import e_store.dto.in.ProductCreateUpdateDto;
import e_store.dto.out.ProductReadDto;
import e_store.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductReadDto> findAll() {
        return productService.findAll();
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

    @DeleteMapping
    public void deleteAll() {
        productService.deleteAll();
    }
}
