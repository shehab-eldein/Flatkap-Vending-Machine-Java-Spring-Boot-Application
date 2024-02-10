package com.flapkap.vendingmachine.service;

import com.flapkap.vendingmachine.model.Product;
import com.flapkap.vendingmachine.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService implements InitializingBean {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService ;


    public Product getById(Integer id) {
        log.info("Get product by id method is called.");
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            return product.get();
        } else {
            throw new RuntimeException("Product not found by id: " + id);
        }
    }

    public Integer create(Product product, Integer userId) {
        log.info("Create product  method is called.");
        product = productRepository.save(product);
        return product.getId();
    }

    public void update(Product product) {
        log.info("Update product  method is called.");
        getById(product.getId());
        productRepository.save(product);

    }
    public List<Product> getAll () {
        log.info("Get all product  method is called.");
        return productRepository.findAll();
    }

    public void delete(Integer productId) {
        log.info("Delete product method is called.");
        productRepository.deleteById(productId);

    }





    @Override
    public void afterPropertiesSet()  {

        Product product = new Product();
        product.setAmountAvailable(3);
        product.setProductName("Pepsi");
        product.setCost(20);
        product.setSellerId(4);
        create(product,4);



        Product product2 = new Product();
        product2.setAmountAvailable(10);
        product2.setProductName("Cola");
        product2.setCost(10);
        product2.setSellerId(3);
        create(product2,3);

    }
}
