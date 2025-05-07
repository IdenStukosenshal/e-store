package e_store.mappers.in;

import e_store.database.entity.Address;
import e_store.database.entity.Order;
import e_store.database.entity.Product;
import e_store.database.entity.User;
import e_store.dto.in.OrderCreateUpdateDto;
import e_store.enums.OrderStatus;
import e_store.mappers.MapperIntrf;
import e_store.repositories.AddressRepo;
import e_store.repositories.ProductRepo;
import e_store.repositories.UserRepo;
import e_store.services.GenerateOrderNumberService;
import e_store.services.OrderCostCalculationService;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class OrderCreateUpdateMapper implements MapperIntrf<OrderCreateUpdateDto, Order> {

    private final UserRepo userRepo;
    private final AddressRepo addressRepo;
    private final ProductRepo productRepo;
    private final OrderCostCalculationService orderCostCalculationService;
    private final GenerateOrderNumberService generateOrderNumberService;

    public OrderCreateUpdateMapper(UserRepo userRepo,
                                   AddressRepo addressRepo,
                                   ProductRepo productRepo,
                                   OrderCostCalculationService orderCostCalculationService,
                                   GenerateOrderNumberService generateOrderNumberService) {
        this.userRepo = userRepo;
        this.addressRepo = addressRepo;
        this.productRepo = productRepo;
        this.orderCostCalculationService = orderCostCalculationService;
        this.generateOrderNumberService = generateOrderNumberService;
    }

    @Override
    public Order map(OrderCreateUpdateDto dto) {
        Order entity = new Order();
        copy(dto, entity);
        return entity;
    }

    @Override
    public Order mapUpd(OrderCreateUpdateDto dto, Order entity) {
        copy(dto, entity);
        return entity;
    }

    private void copy(OrderCreateUpdateDto dto, Order entity) {
        entity.setUser(findUser(dto.userId()));
        entity.setStatus(OrderStatus.NEW);
        entity.setAddress(findAddress(dto.addressId()));

        Map<Long, Integer> productsMap = dto.productQuantityIdMap();
        List<Product> productLst = findProducts(productsMap.keySet());
        for (var pr : productLst) {
            entity.addProduct(pr, productsMap.get(pr.getId()));
        }
        entity.setOrderCost(orderCostCalculationService.calculate(entity.getOrderProductLst()));
        entity.setOrderNumber(generateOrderNumberService.generate(entity));
    }


    private User findUser(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new ValidationException("not found, TEXT!1!!"));
    }

    private Address findAddress(Long id) {
        return addressRepo.findById(id).orElseThrow(() -> new ValidationException("not found, TEXT!1!!"));
    }

    private List<Product> findProducts(Set<Long> productsIdSet) {
        return productRepo.findAllById(productsIdSet);
    }
}
