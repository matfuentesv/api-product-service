package cl.company.apiproductservice.service.impl;

import cl.company.apiproductservice.client.UserClient;
import cl.company.apiproductservice.exception.ApiResponse;
import cl.company.apiproductservice.model.Product;
import cl.company.apiproductservice.service.AuthService;
import cl.company.apiproductservice.service.ProductService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@Log
public class AuthServiceImpl implements AuthService {

    private final ProductService productService;
    private final UserClient userClient;

    @Autowired
    public AuthServiceImpl(ProductService productService, UserClient userClient) {
        this.productService = productService;
        this.userClient = userClient;
    }

    @Override
    public ResponseEntity<Object> findProduct(String username, String password, Long id) {
        final boolean userValid = isValidUser(username,password);
        if(userValid){
            return ResponseEntity.ok(productService.findProduct(id));
        }else {
            return new ResponseEntity<>("No esta Autorizado para obtener información del usuario", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> findAllProduct(String username, String password) {
        final boolean userValid = isValidUser(username,password);
        if(userValid){
            return ResponseEntity.ok(productService.findAllProduct());
        }else {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> createProduct(String username, String password, Product product) {
        final boolean userValid = isValidUser(username,password);
        if(userValid){
            if(!productService.existsProductByName(product.getName())){
                final Product createdProduct = productService.createProduct(product);
                if(createdProduct == null){
                    log.info("Algunos de los parámetros no se ingresaron");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Algunos de los parámetros no se ingresaron",false));
                }else {
                    return ResponseEntity.ok(product);
                }
            }   else {
                log.info("No se puedo crear el cliente,ya existe");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("No se puedo crear el cliente,ya existe",false));
            }

        }else {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> updateProduct(String username, String password, Product product) {
        final boolean userValid = isValidUser(username,password);
        if(userValid){
            if(productService.existsProductById((long) product.getId())){
                return ResponseEntity.ok(productService.updateProduct(product));
            }   else {
                log.info("No se puedo actualizar el product,no existe");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("No se puedo actualizar el product,no existe",false));
            }
        }else {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Object> deleteProduct(String username, String password, Long id) {
        // Verificar la autenticación del usuario
        boolean userValid = isValidUser(username,password);
        if (!userValid) {
            return new ResponseEntity<>("No esta Autorizado para ejecutar este endpoint", HttpStatus.UNAUTHORIZED);
        }

        // Verificar si el customer existe
        if (!productService.existsProductById(id)) {
            log.info("No se puede eliminar el product, no existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("El product no existe",false));
        }

        productService.deleteProduct(id);
        return ResponseEntity.ok("Eliminación exitosa");
    }


    private boolean isValidUser(String username,String password){
        return userClient.login(username,password).isSuccess();
    }
}
