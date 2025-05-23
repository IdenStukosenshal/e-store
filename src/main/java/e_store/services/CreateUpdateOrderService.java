package e_store.services;

import e_store.database.entity.Order;
import e_store.database.entity.Product;
import e_store.database.entity.User;
import e_store.dto.in.OrderCreateUpdateDto;
import e_store.enums.OrderStatus;
import e_store.mappers.in.AddressCreateMapper;
import e_store.repositories.ProductRepo;
import e_store.repositories.UserRepo;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CreateUpdateOrderService {

    private final ValidateAddressService validateAddressService;

    private final UserRepo userRepo;
    private final AddressCreateMapper addressCreateMapper;
    private final ProductRepo productRepo;
    private final OrderCostCalculationService orderCostCalculationService;
    private final GenerateOrderNumberService generateOrderNumberService;

    public CreateUpdateOrderService(ValidateAddressService validateAddressService,
                                    UserRepo userRepo,
                                    AddressCreateMapper addressCreateMapper,
                                    ProductRepo productRepo,
                                    OrderCostCalculationService orderCostCalculationService,
                                    GenerateOrderNumberService generateOrderNumberService) {
        this.validateAddressService = validateAddressService;
        this.userRepo = userRepo;
        this.addressCreateMapper = addressCreateMapper;
        this.productRepo = productRepo;
        this.orderCostCalculationService = orderCostCalculationService;
        this.generateOrderNumberService = generateOrderNumberService;
    }

    public Order create(OrderCreateUpdateDto dto) {
        Order order = new Order();
        order.setStatus(OrderStatus.NEW);

        fillOrderFields(dto, order);
        order.setOrderNumber(generateOrderNumberService.generate(order));
        return order;
    }

    public Order update(OrderCreateUpdateDto dto, Order order) {
        if (!dto.productQuantityIdMap().isEmpty()) {
            order.getOrderProductLst().clear();
        }

        fillOrderFields(dto, order);

        return order;
    }

    private void fillOrderFields(OrderCreateUpdateDto dto, Order order) {
        order.setUser(findUser(dto.userId()));
        order.setAddress(validateAddressService.validAndCorrect(addressCreateMapper.map(dto.addressCreateDto())));

        Map<Long, Integer> incProductsIDMap = dto.productQuantityIdMap();
        List<Product> incVerifiedProductLst = findProducts(incProductsIDMap.keySet());
        for (var product : incVerifiedProductLst) {
            order.addProduct(product, incProductsIDMap.get(product.getId()));
        }
        order.setOrderCost(orderCostCalculationService.calculate(order.getOrderProductLst()));
    }

    private User findUser(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new ValidationException("User not found!"));
    }

    private List<Product> findProducts(Set<Long> productsIdSet) {
        List<Product> productsLst = productRepo.findAllById(productsIdSet);
        if(productsLst.isEmpty()) throw new ValidationException("Products not found!");
        return productsLst;
    }
}