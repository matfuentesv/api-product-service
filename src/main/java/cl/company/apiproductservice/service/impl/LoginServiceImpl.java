package cl.company.apiproductservice.service.impl;

import cl.company.apiproductservice.exception.ApiResponse;
import cl.company.apiproductservice.model.Product;
import cl.company.apiproductservice.repository.UserRepository;
import cl.company.apiproductservice.service.LoginService;
import cl.company.apiproductservice.service.ProductService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@Log
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<Object> findProduct(String username, String password, Long id) {
        final boolean userValid = userRepository.findByUserPassword(username,password).isPresent();
        if(userValid){
            return ResponseEntity.ok(productService.findProduct(id));
        }else {
            return new ResponseEntity<>("No esta Autorizado para obtener información del usuario", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> findAllProduct(String username, String password) {
        final boolean userValid = userRepository.findByUserPassword(username,password).isPresent();
        if(userValid){
            return ResponseEntity.ok(productService.findAllProduct());
        }else {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> createProduct(String username, String password, Product product) {
        final boolean userValid = userRepository.findByUserPassword(username,password).isPresent();
        if(userValid){
            if(!productService.existsProduct(product.getName())){
                final Product createdProduct = productService.createProduct(product);
                if(createdProduct == null){
                    log.info("Algunos de los parámetros no se ingresaron");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Algunos de los parámetros no se ingresaron"));
                }else {
                    return ResponseEntity.ok(product);
                }
            }   else {
                log.info("No se puedo crear el cliente,ya existe");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("No se puedo crear el cliente,ya existe"));
            }

        }else {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> updateProduct(String username, String password, Product product) {
        final boolean userValid = userRepository.findByUserPassword(username,password).isPresent();
        if(userValid){
            if(productService.existsProduct(product.getName())){
                return ResponseEntity.ok(productService.updateProduct(product));
            }   else {
                log.info("No se puedo actualizar el product,no existe");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("No se puedo actualizar el product,no existe"));
            }
        }else {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> deleteProduct(String username, String password, Long id) {
        return null;
    }
}
