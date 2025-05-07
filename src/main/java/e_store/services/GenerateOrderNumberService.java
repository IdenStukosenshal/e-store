package e_store.services;

import e_store.database.entity.Order;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GenerateOrderNumberService {

    //TODO черновой вариант
    public String generate(Order entity) {
        return Integer.toString(Objects.hash(entity.getUser(), entity.getCreatedAt(), entity.getAddress(), entity.getOrderProductLst()));
    }
}
