package cl.company.apiproductservice.service.impl;

import cl.company.apiproductservice.model.Product;
import cl.company.apiproductservice.repository.ProductRepository;
import cl.company.apiproductservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product findProduct(Long id) {
        if(productRepository.findById(id).isPresent()){
            return productRepository.findById(id).get();
        } else {
            return new Product();
        }
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsProductByName(String name) {
        return productRepository.findProductByName(name).isPresent();
    }

    @Override
    public boolean existsProductById(Long id) {
        return productRepository.findProductById(id).isPresent();
    }
}
