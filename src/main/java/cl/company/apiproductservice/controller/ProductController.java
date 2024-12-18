package cl.company.apiproductservice.controller;

import cl.company.apiproductservice.exception.ApiResponse;
import cl.company.apiproductservice.model.Product;
import cl.company.apiproductservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Log
public class ProductController {

    @Autowired
    AuthService authService;


    @GetMapping(value = "/findAllProduct", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAllProduct(@RequestHeader("username")String username,
                                                 @RequestHeader("password")String password){
        log.info("Se solicita la lista de todas los usuarios");
        return ResponseEntity.ok(authService.findAllProduct(username,password));
    }

    @GetMapping("/findProduct/{id}")
    public ResponseEntity<Object> findProduct(@RequestHeader("username")String username,
                                              @RequestHeader("password")String password,
                                              @PathVariable Long id) {

        if (StringUtils.containsWhitespace(String.valueOf(id)) || id == null) {
            log.info("El id no se ingreso");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Algunos de los parámetros no se ingresaron",false));
        }
        return ResponseEntity.ok(authService.findProduct(username,password,id));
    }

    @PostMapping("/createProduct")
    public ResponseEntity<Object> createProduct(@RequestHeader("username")String username,
                                                @RequestHeader("password")String password,
                                                @Valid @RequestBody Product product,
                                                BindingResult bindingResult) throws MethodArgumentNotValidException {

        if (product == null) {
            log.info("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Algunos de los parámetros no se ingresaron",false));
        }

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Error en los argumentos del método.");
        }

        return ResponseEntity.ok(authService.createProduct(username,password,product));
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<Object> updateProduct(@RequestHeader("username")String username,
                                                @RequestHeader("password")String password,
                                                @Valid @RequestBody Product product,
                                                BindingResult bindingResult) throws MethodArgumentNotValidException {

        if (product == null) {
            log.info("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Algunos de los parámetros no se ingresaron",false));
        }

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Error en los argumentos del método.");
        }

        return ResponseEntity.ok(authService.updateProduct(username,password,product));
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Object> deleteProduct(@RequestHeader("username")String username,
                                                @RequestHeader("password")String password,
                                                @PathVariable Long id) {

        if (StringUtils.containsWhitespace(String.valueOf(id))|| id == null) {
            log.info("El id no se ingreso");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Algunos de los parámetros no se ingresaron",false));
        }


        Object product = authService.findProduct(username,password, id);

        if (product != null) {
            authService.deleteProduct(username,password,id);
            return ResponseEntity.ok(new ApiResponse("Producto eliminado",true));
        } else {
            log.info("Producto no encontrado con id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Libro no encontrado",false));
        }
    }



}
