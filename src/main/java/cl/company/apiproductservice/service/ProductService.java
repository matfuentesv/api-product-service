package cl.company.apiproductservice.service;

import cl.company.apiproductservice.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();
    Product findProduct(Long id);
    Product createProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(Long id);
}
