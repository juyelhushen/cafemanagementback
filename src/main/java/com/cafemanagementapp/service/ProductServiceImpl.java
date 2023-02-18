package com.cafemanagementapp.service;

import com.cafemanagementapp.constant.CafeConstants;
import com.cafemanagementapp.dao.ProductDao;
import com.cafemanagementapp.dao.UserDao;
import com.cafemanagementapp.entity.Category;
import com.cafemanagementapp.entity.Product;
import com.cafemanagementapp.jwt.JwtAuthFilter;
import com.cafemanagementapp.utils.CafeUtils;
import com.cafemanagementapp.wrapper.ProductRequest;
import com.cafemanagementapp.wrapper.ProductStatusRequest;
import com.cafemanagementapp.wrapper.ProductWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductDao productDao;
    private final JwtAuthFilter filter;
    private final UserDao userDao;

    @Override
    public ResponseEntity<String> addProduct(ProductRequest request) {
        try {
            if (filter.isAdmin()) {
                productDao.save(saveProduct(request));
                return CafeUtils.getResponse("Product has been added Successfully",HttpStatus.OK);
            } else {
                return CafeUtils.getResponse(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Product saveProduct(ProductRequest request) {
        Category category = new Category();
        category.setId(request.getCategoryId());
        return Product.builder()
                .name(request.getName())
                .category(category)
                .description(request.getDescription())
                .price(request.getPrice())
                .status(true)
                .build();
    }

//    private boolean validateProduct(ProductRequest request) {
//        return request.getName() != null
//                && request.getPrice() != null;
//    }


    @Override
    public ResponseEntity<List<ProductWrapper>> getProduct() {
        try {
            return new ResponseEntity<List<ProductWrapper>>(productDao.getAllProducts(),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(ProductRequest request, Integer id) {
        try {
            if (filter.isAdmin()){
                Optional<Product> optional = productDao.findById(id);
                if (optional.isPresent()) {
                   Category category = new Category();
                   category.setId(request.getCategoryId());
                   Product product = optional.get();
                   product.setName(request.getName());
                   product.setDescription(request.getDescription());
                   product.setCategory(category);
                   product.setPrice(request.getPrice());
                   product.setStatus(optional.get().getStatus());
                   productDao.save(product);
                    return CafeUtils.getResponse("Product Updated successfully",HttpStatus.OK);
                } else {
                    return CafeUtils.getResponse("Product id does not exist",HttpStatus.NOT_FOUND);
                }
            } else {
                return CafeUtils.getResponse(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try {
            if (filter.isAdmin()) {
                Optional<Product> product = productDao.findById(id);
                if (product.isPresent()) {
                    productDao.deleteById(id);
                    return CafeUtils.getResponse("Product deleted successfully",HttpStatus.OK);
                } else {
                    return CafeUtils.getResponse("Product id does not exist", HttpStatus.NOT_FOUND);
                }
            } else {
                return CafeUtils.getResponse(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProductStatus(ProductStatusRequest request, Integer id) {
        try {
            if (filter.isAdmin()) {
                Optional<Product> optional = productDao.findById(id);
                if (optional.isPresent()) {
                    productDao.updateStatus(request.getStatus(), id);
                    return CafeUtils.getResponse("Product status Updated Successfully", HttpStatus.OK);
                } else {
                    return CafeUtils.getResponse("Product id does not exist.",HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponse(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getProductByCategoryId(Integer id) {
        try {
            return new ResponseEntity<List<ProductWrapper>>(productDao.getProductByCategoryId(id),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getProductById(Integer id) {
        try {
            return new ResponseEntity<List<ProductWrapper>>(productDao.getProductById(id),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
