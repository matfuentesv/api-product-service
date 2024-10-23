package cl.company.apiproductservice.repository;

import cl.company.apiproductservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
