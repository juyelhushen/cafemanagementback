package com.cafemanagementapp.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtRegister {
    private String name;
    private String contactNumber;
    private String email;
    private String password;


}
