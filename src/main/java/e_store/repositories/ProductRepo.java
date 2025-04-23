package e_store.repositories;

import e_store.database.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name = :name")
    Optional<Product> findByName(String name);


    //@Query(value = "", nativeQuery = true) для чистого SQL, для select

    //@Modifying(clearAutomatically = true) для update, параметр для избежания проблем с кешем, когда в нём осталось старое значение и следующий select берёт его
    //@Query(...)

    //Проекция - вернуть другой dto, всё работает автоматически
    //@Query("какой-то запрос HQL")
    //public Type findOtherData( params ...)
    /*
    Но для нативного запроса нужен интерфейс(в нём getters)

    @Query(value = " select столбцы соответствующие геттерам ", nativeQuery = true)
    //public InterfaceName findOtherData( params ...)

    */
}
