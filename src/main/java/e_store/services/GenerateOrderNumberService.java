package e_store.services;

import e_store.database.entity.Order;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GenerateOrderNumberService {

    //TODO черновой вариант
    public String generate(Order entity) {
        return UUID.randomUUID().toString();
    }
}
