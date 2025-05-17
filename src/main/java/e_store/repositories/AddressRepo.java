package e_store.repositories;

import e_store.database.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address, Long> {

    @Query(value = """
            SELECT *
              FROM s_address a
             WHERE a.user_id = :userId
            """, nativeQuery = true)
    List<Address> findAllByUserId(Long userId);

    @Query(value = """
            DELETE
              FROM s_address a
             WHERE a.user_id = :userId
            """, nativeQuery = true)
    void deleteAllByUserId(Long userId);
}
