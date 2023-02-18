package com.cafemanagementapp.service;

import com.cafemanagementapp.entity.Category;
import com.cafemanagementapp.wrapper.CategoryRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    ResponseEntity<String> addCategory(CategoryRequest request);


    ResponseEntity<List<Category>> getCategory(String filterValue);

    ResponseEntity<String> updateCategory(CategoryRequest request,Integer id);
}
