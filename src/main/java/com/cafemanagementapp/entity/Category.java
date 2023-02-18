package com.cafemanagementapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@NamedQuery(name = "Category.getAllCategories", query = "SELECT c FROM Category c WHERE c.id in (SELECT p.category FROM Product p WHERE p.status =true)")

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category implements Serializable {
    private static final long serialVersionUID = 2L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

}
