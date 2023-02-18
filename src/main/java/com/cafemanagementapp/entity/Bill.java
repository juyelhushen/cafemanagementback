package com.cafemanagementapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@NamedQuery(name = "Bill.getAllBill", query = "SELECT new com.cafemanagementapp.wrapper.BillWrapper(b.id,b.uuid,b.name,b.email,b.contactNumber,b.paymentMethod,b.total,b.productDetails,b.createdBy) FROM Bill b Order by b.id DESC")
@NamedQuery(name = "Bill.getUserBill", query = "SELECT new com.cafemanagementapp.wrapper.BillWrapper(b.id,b.uuid,b.name,b.email,b.contactNumber,b.paymentMethod,b.total,b.productDetails,b.createdBy) FROM Bill b WHERE b.createdBy = : username Order by b.id DESC")

@Entity
@Table(name = "bill")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bill implements Serializable {

    private static final long serialVersionUID = 4L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid;
    private String name;
    private String email;
    @Column(name = "contactnumber")
    private String contactNumber;
    @Column(name = "paymentmethod")
    private String paymentMethod;

    private Integer total;
    @Column(name = "productdetails",columnDefinition = "json")
    private String productDetails;
    @Column(name = "createdby")
    private String createdBy;

}
