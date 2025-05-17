package e_store.services;


import e_store.dto.in.ProductCreateUpdateDto;
import e_store.dto.out.ProductReadDto;
import e_store.mappers.in.ProductCreateUpdateMapper;
import e_store.mappers.out.ProductReadMapper;
import e_store.repositories.ProductRepo;
import jakarta.validation.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
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

    @Transactional(readOnly = true)
    public Page<ProductReadDto> findAll(PageRequest pageRequest) {
        return productRepo
                .findAll(pageRequest)
                .map(productReadMapper::map);
    }

    @Transactional(readOnly = true)
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
        var savedEntity = productRepo.save(updatedEntity);
        return productReadMapper.map(savedEntity);
    }

    public void deleteById(Long id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            productRepo.flush();
        } else {
            throw new ValidationException("not found, TEXT!1!!");
        }
    }

    public void deleteAll() {
        productRepo.deleteAll();
    }


}
