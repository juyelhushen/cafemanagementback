package com.cafemanagementapp.rest;

import com.cafemanagementapp.constant.CafeConstants;
import com.cafemanagementapp.entity.Category;
import com.cafemanagementapp.service.CategoryService;
import com.cafemanagementapp.utils.CafeUtils;
import com.cafemanagementapp.wrapper.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CategoryRestImpl implements CategoryRest{

    @Autowired
    private final CategoryService service;

    @Override
    public ResponseEntity<String> addCategory(CategoryRequest request) {
        try {
            return service.addCategory(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try {
            return service.getCategory(filterValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateCategory(CategoryRequest request, Integer id) {
        try {
            return service.updateCategory(request,id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
