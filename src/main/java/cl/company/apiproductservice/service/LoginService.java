package cl.company.apiproductservice.service;

import cl.company.apiproductservice.model.Product;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    ResponseEntity<Object> findProduct(String username, String password, Long id);
    ResponseEntity<Object>findAllProduct(String username,String password);
    ResponseEntity<Object> createProduct(String username, String password, Product product);
    ResponseEntity<Object> updateProduct(String username,String password,Product product);
    ResponseEntity<Object> deleteProduct(String username,String password,Long id);

}
