package com.example.productservicefirstproject.controllers;

import com.example.productservicefirstproject.exceptions.ProductNotExistsException;
import com.example.productservicefirstproject.model.Product;
import com.example.productservicefirstproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping()
    public List<Product> getAllProducts(){

//        ResponseEntity<List<Product>> response = new ResponseEntity<>(
//                productService.getAllProducts(), HttpStatus.FORBIDDEN
//        );
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getSingleProduct(@PathVariable("id") Long id) throws ProductNotExistsException {
        return productService.getSingleProduct(id);
    }

    @PostMapping("/addNewProduct")
    public Product addNewProduct(@RequestBody Product product) {
        return productService.addNewProduct(product);
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        return new Product();
    }

    @PutMapping("/{id}")
    public Product fullyUpdateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        return new Product();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
//        return new Product();
    }
}
