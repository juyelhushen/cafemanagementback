package com.cafemanagementapp.service;

import com.cafemanagementapp.constant.CafeConstants;
import com.cafemanagementapp.dao.CategoryDao;
import com.cafemanagementapp.dao.UserDao;
import com.cafemanagementapp.entity.Category;
import com.cafemanagementapp.jwt.JwtAuthFilter;

import com.cafemanagementapp.utils.CafeUtils;
import com.cafemanagementapp.wrapper.CategoryRequest;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryDao categoryDao;
    private final JwtAuthFilter filter;
    private final UserDao userDao;

    @Override
    public ResponseEntity<String> addCategory(CategoryRequest request) {
        try {
            if (filter.isAdmin()) {
                if (validateCategory(request)) {
                    categoryDao.save(saveCategory(request));
                    return CafeUtils.getResponse("Category Added Successfully", HttpStatus.OK);
                } else {
                    return CafeUtils.getResponse(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponse(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Category saveCategory(CategoryRequest request) {
        return Category.builder()
                .name(request.getName())
                .build();
    }


    private boolean validateCategory(CategoryRequest request) {
        return request.getName() != null;
    }

    @Override
    public ResponseEntity<List<Category>> getCategory(String filterValue) {
        try {
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
                log.info("inside filter if");
                return new ResponseEntity<List<Category>>(categoryDao.getAllCategories(), HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Category>>(categoryDao.findAll(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(CategoryRequest request, Integer id) {
        try {
            if (filter.isAdmin()) {
                Optional<Category> optional = categoryDao.findById(id);
                if (optional.isPresent()) {
                    Category category = optional.get();
                    category.setName(request.getName());
                    categoryDao.save(category);
                    return CafeUtils.getResponse("Category updated successfully", HttpStatus.OK);
                } else {
                    return CafeUtils.getResponse("Category id not found", HttpStatus.NOT_FOUND);
                }
            } else {
                return CafeUtils.getResponse(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
