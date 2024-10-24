package cl.company.apiproductservice.service;

import cl.company.apiproductservice.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAllProduct();
    Product findProduct(Long id);
    Product createProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(Long id);
    boolean existsProduct(String username);
}
