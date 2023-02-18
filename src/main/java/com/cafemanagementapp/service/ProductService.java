package com.cafemanagementapp.service;

import com.cafemanagementapp.wrapper.ProductRequest;
import com.cafemanagementapp.wrapper.ProductStatusRequest;
import com.cafemanagementapp.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<String> addProduct(ProductRequest request);

    ResponseEntity<List<ProductWrapper>> getProduct();

    ResponseEntity<String> updateProduct(ProductRequest request, Integer id);

    ResponseEntity<String> deleteProduct(Integer id);

    ResponseEntity<String> updateProductStatus(ProductStatusRequest request, Integer id);

    ResponseEntity<List<ProductWrapper>> getProductByCategoryId(Integer id);

    ResponseEntity<List<ProductWrapper>> getProductById(Integer id);
}
