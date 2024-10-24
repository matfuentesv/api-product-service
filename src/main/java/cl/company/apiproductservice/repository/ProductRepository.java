package cl.company.apiproductservice.repository;

import cl.company.apiproductservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT u FROM Product u WHERE u.name = :name")
    Optional<Product> findProductByName(@Param("name") String name);
}
