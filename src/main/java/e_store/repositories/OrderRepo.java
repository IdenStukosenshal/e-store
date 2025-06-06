package e_store.repositories;

import e_store.database.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrderRepo extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<Order> {
}
