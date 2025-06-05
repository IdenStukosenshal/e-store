package e_store.services;


import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import e_store.database.entity.QProduct;
import e_store.dto.filter.ProductFilter;
import e_store.dto.in.ProductCreateUpdateDto;
import e_store.dto.out.ProductReadDto;
import e_store.enums.ErrorCode;
import e_store.exceptions.LocalizedValidationException;
import e_store.mappers.in.ProductCreateUpdateMapper;
import e_store.mappers.out.ProductReadMapper;
import e_store.repositories.ProductRepo;
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
    public Page<ProductReadDto> findAll(ProductFilter productFilter, PageRequest pageRequest) {
        Predicate predicate = buildPredicate(productFilter);
        return productRepo
                .findAll(predicate, pageRequest)
                .map(productReadMapper::map);
    }

    @Transactional(readOnly = true)
    public ProductReadDto findById(Long id) {
        var entity = productRepo.findById(id).orElseThrow(() -> new LocalizedValidationException(ErrorCode.NOT_FOUND.getMsg(), "Product"));
        return productReadMapper.map(entity);
    }

    public ProductReadDto create(ProductCreateUpdateDto dto) {
        var entity = productCreateUpdateMapper.map(dto);
        var savedEntity = productRepo.save(entity);
        return productReadMapper.map(savedEntity);
    }

    public ProductReadDto update(Long id, ProductCreateUpdateDto updateDto) {
        var entity = productRepo.findById(id).orElseThrow(() -> new LocalizedValidationException(ErrorCode.NOT_FOUND.getMsg(), "Product"));
        var updatedEntity = productCreateUpdateMapper.mapUpd(updateDto, entity);
        var savedEntity = productRepo.save(updatedEntity);
        return productReadMapper.map(savedEntity);
    }

    public void deleteById(Long id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            productRepo.flush();
        } else {
            throw new LocalizedValidationException(ErrorCode.NOT_FOUND.getMsg(), "Product");
        }
    }

    private Predicate buildPredicate(ProductFilter productFilter) {

        var product = QProduct.product;
        BooleanExpression predicate = product.isNotNull();
        if (productFilter.name() != null) {
            predicate = predicate.and(product.name.contains(productFilter.name()));
        }
        if (productFilter.minPrice() != null) {
            predicate = predicate.and(product.price.goe(productFilter.minPrice()));
        }
        if (productFilter.maxPrice() != null) {
            predicate = predicate.and(product.price.loe(productFilter.maxPrice()));
        }
        return predicate;
    }
}
