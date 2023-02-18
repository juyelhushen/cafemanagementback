package com.cafemanagementapp.rest;

import com.cafemanagementapp.entity.Category;
import com.cafemanagementapp.wrapper.CategoryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/category")
public interface CategoryRest {

    @PostMapping("/add")
    public ResponseEntity<String> addCategory(@RequestBody CategoryRequest request);

    @GetMapping("/get")
    public ResponseEntity<List<Category>> getAllCategory(@RequestParam(required = false) String filterValue);

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryRequest request ,@PathVariable Integer id);
}
