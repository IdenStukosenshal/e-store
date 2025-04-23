package e_store.repositories;

import e_store.database.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderProductRepo extends JpaRepository<OrderProduct, Long> {

    @Query(value = """
               SELECT op.id, op.order_id, op.product_id, op.quantity
               FROM s_order_product op
               INNER JOIN s_order o ON op.order_id = o.order_id
               WHERE o.user_id = :id
               """,
            nativeQuery = true)
    List<OrderProduct> findAllByUserId(Long id);
}
