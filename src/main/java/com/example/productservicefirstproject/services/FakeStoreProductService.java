package com.example.productservicefirstproject.services;


import com.example.productservicefirstproject.dtos.FakeStoreProductDto;
import com.example.productservicefirstproject.exceptions.ProductNotExistsException;
import com.example.productservicefirstproject.model.Category;
import com.example.productservicefirstproject.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FakeStoreProductService implements ProductService{

    private RestTemplate restTemplate;

    HashMap<Long, Product> map = new HashMap<>();

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public Product ConvertFakeStoreProductToProduct(FakeStoreProductDto fakeStoreProduct){
        Product product = new Product();
        product.setId(fakeStoreProduct.getId());
        product.setTitle(fakeStoreProduct.getTitle());
        product.setPrice(fakeStoreProduct.getPrice());
        product.setDescription(fakeStoreProduct.getDescription());
        product.setCategory(new Category());
        product.setImageUrl(fakeStoreProduct.getImage());
        product.getCategory().setName(fakeStoreProduct.getCategory());

        return product;
    }
    @Override
    public Product getSingleProduct(Long id) throws ProductNotExistsException {
        FakeStoreProductDto productDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class
        );

        if(productDto == null){
            throw new ProductNotExistsException(
                    "Product with id: " + id + " doesn't exists."
            );
        }
        return ConvertFakeStoreProductToProduct(productDto);
    }

    @Override
    public List<Product> getAllProducts(){

         FakeStoreProductDto[] response = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );

        List<Product> answer = new ArrayList<>();

        //Adding third party Product's
        for(FakeStoreProductDto prod : response){
            answer.add(ConvertFakeStoreProductToProduct(prod));
            map.put(prod.getId(), ConvertFakeStoreProductToProduct(prod));
        }

        //This is used for adding third party and newly added product in the List.
        for(Map.Entry<Long, Product> entry : map.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
            answer.add(entry.getValue());
        }

        return answer;
    }

    @Override
    public Product addNewProduct(Product product) {
        FakeStoreProductDto productDto = new FakeStoreProductDto();

        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setImage(product.getImageUrl());
        productDto.setCategory(product.getCategory().getName());

        map.put(product.getId(), product);

        return ConvertFakeStoreProductToProduct(productDto);
    }
}
