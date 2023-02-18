package com.cafemanagementapp.wrapper;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillWrapper {

    private Integer id;
    private String uuid;
    private String name;
    private String email;

    private String contactNumber;

    private String paymentMethod;

    private Integer total;

    private String productDetails;

    private String createdBy;



}
