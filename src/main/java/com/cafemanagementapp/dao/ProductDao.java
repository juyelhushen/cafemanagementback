package com.cafemanagementapp.dao;

import com.cafemanagementapp.entity.Product;
import com.cafemanagementapp.wrapper.ProductWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

    List<ProductWrapper> getAllProducts();

    @Transactional
    @Modifying
    void updateStatus(@Param("status") Boolean status,@Param("id") Integer id);

    List<ProductWrapper> getProductByCategoryId(Integer id);

    List<ProductWrapper> getProductById(Integer id);
}
