package com.cafemanagementapp.wrapper;

import com.cafemanagementapp.entity.Category;
import lombok.Data;

@Data
public class ProductRequest {
    private Integer categoryId;
    private String name;
    private Category category;
    private String description;
    private double price;
    private Boolean status;
}
