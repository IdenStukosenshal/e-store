package e_store.repositories;

import e_store.database.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepo extends JpaRepository<ProductOrder, Long> {
}
