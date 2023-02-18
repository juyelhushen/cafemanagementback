package com.cafemanagementapp.entity;

import jakarta.persistence.*;
import lombok.*;

@NamedQuery(name = "Product.getAllProducts",query = "SELECT new com.cafemanagementapp.wrapper.ProductWrapper(p.id,p.name,p.description,p.price,p.status,p.category.id,p.category.name) From Product p")
@NamedQuery(name = "Product.getProductByCategoryId",query = "SELECT new com.cafemanagementapp.wrapper.ProductWrapper(p.id,p.name) From Product p WHERE p.category.id = :id AND p.status = true")
@NamedQuery(name = "Product.getProductById",query = "SELECT new com.cafemanagementapp.wrapper.ProductWrapper(p.id,p.name,p.description,p.price) From Product p WHERE p.id = :id")
@NamedQuery(name = "Product.updateStatus", query = "Update Product p Set p.status = :status Where p.id = :id")

@Entity
@Builder
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private static final String serialVersionUID = "3L";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;

    private String description;
    private double price;

    private Boolean status;


}
