package cl.company.apiproductservice.controller;

import cl.company.apiproductservice.exception.ApiResponse;
import cl.company.apiproductservice.model.Product;
import cl.company.apiproductservice.service.ProductService;
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

import java.util.List;

@RestController
@RequestMapping("/api")
@Log
public class ProductController {

    @Autowired
    ProductService productService;


    @GetMapping(value = "/findAllProduct", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> findAllProduct() {
        log.info("Se solicita la lista de todas los usuarios");
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/findProduct/{id}")
    public ResponseEntity<Object> findProduct(@PathVariable Long id) {

        if (StringUtils.containsWhitespace(String.valueOf(id)) || id == null) {
            log.info("El id no se ingreso");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Algunos de los parámetros no se ingresaron"));
        }
        return ResponseEntity.ok(productService.findProduct(id));
    }

    @PostMapping("/createProduct")
    public ResponseEntity<Object> createProduct(@Valid @RequestBody Product product,
                                             BindingResult bindingResult) throws MethodArgumentNotValidException {

        if (product == null) {
            log.info("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Algunos de los parámetros no se ingresaron"));
        }

        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }

        return ResponseEntity.ok(productService.createProduct(product));
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<Object> updateProduct(@Valid @RequestBody Product product,
                                             BindingResult bindingResult) throws MethodArgumentNotValidException {

        if (product == null) {
            log.info("Algunos de los parámetros no se ingresaron");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Algunos de los parámetros no se ingresaron"));
        }

        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }

        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {

        if (StringUtils.containsWhitespace(String.valueOf(id))|| id == null) {
            log.info("El id no se ingreso");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Algunos de los parámetros no se ingresaron"));
        }


        Product product = productService.findProduct(id);

        if (product != null) {
            productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponse("Libro eliminado"));
        } else {
            log.info("Libro no encontrado con id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Libro no encontrado"));
        }
    }



}
