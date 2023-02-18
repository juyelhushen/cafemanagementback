package com.cafemanagementapp.rest;

import com.cafemanagementapp.wrapper.ProductRequest;
import com.cafemanagementapp.wrapper.ProductStatusRequest;
import com.cafemanagementapp.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/product")
public interface ProductRest {

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest request);

    @GetMapping("/get")
    public ResponseEntity<List<ProductWrapper>> getProduct();

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@RequestBody ProductRequest request,@PathVariable Integer id);


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id);

    @PutMapping("/updatestatus/{id}")
    public ResponseEntity<String> updateProductStatus(@RequestBody ProductStatusRequest request, @PathVariable Integer id);

    @GetMapping("/getproductbycategory/{id}")
    public ResponseEntity<List<ProductWrapper>> getProductByCategoryId(@PathVariable Integer id);

    @GetMapping("/getproductbyid/{id}")
    public ResponseEntity<List<ProductWrapper>> getProductById(@PathVariable Integer id);

}
