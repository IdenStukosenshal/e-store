package e_store.services;


import e_store.dto.in.ProductCreateUpdateDto;
import e_store.dto.out.ProductReadDto;
import e_store.mappers.in.ProductCreateUpdateMapper;
import e_store.mappers.out.ProductReadMapper;
import e_store.repositories.ProductRepo;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductCreateUpdateMapper productCreateUpdateMapper;
    private final ProductReadMapper productReadMapper;
    private final ProductRepo productRepo;

    public ProductService(ProductCreateUpdateMapper productCreateUpdateMapper,
                          ProductReadMapper productReadMapper,
                          ProductRepo productRepo) {
        this.productCreateUpdateMapper = productCreateUpdateMapper;
        this.productReadMapper = productReadMapper;
        this.productRepo = productRepo;
    }

    public List<ProductReadDto> findAll() {
        return productRepo
                .findAll()
                .stream()
                .map(productReadMapper::map)
                .toList();
    }

    public ProductReadDto findById(Long id) {
        var entity = productRepo.findById(id).orElseThrow(() -> new ValidationException("not found, TEXT!1!!"));
        return productReadMapper.map(entity);
    }

    public ProductReadDto create(ProductCreateUpdateDto dto) {
        var entity = productCreateUpdateMapper.map(dto);
        var savedEntity = productRepo.save(entity);
        return productReadMapper.map(savedEntity);
    }

    public ProductReadDto update(Long id, ProductCreateUpdateDto updateDto) {
        var entity = productRepo.findById(id).orElseThrow(() -> new ValidationException("not found, TEXT!1!!"));
        var updatedEntity = productCreateUpdateMapper.mapUpd(updateDto, entity);
        var savedEntity = productRepo.save(updatedEntity); //saveAndFlush()
        return productReadMapper.map(savedEntity);
    }

    public void deleteById(Long id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
        } else {
            throw new ValidationException("not found, TEXT!1!!");
        }
    }

    public void deleteAll() {
        productRepo.deleteAll();
    }


}
