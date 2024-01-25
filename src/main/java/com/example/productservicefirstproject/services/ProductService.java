package com.example.productservicefirstproject.services;

import com.example.productservicefirstproject.exceptions.ProductNotExistsException;
import com.example.productservicefirstproject.model.Product;

import java.util.List;

public interface ProductService {

    Product getSingleProduct(Long id) throws ProductNotExistsException;

    List<Product> getAllProducts();

    Product addNewProduct(Product product);
}
