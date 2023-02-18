package com.cafemanagementapp.service;

import com.cafemanagementapp.wrapper.ForgotPasswordRequest;
import com.cafemanagementapp.wrapper.PasswordChangeRequest;
import com.cafemanagementapp.wrapper.UserActivateRequest;
import com.cafemanagementapp.jwt.model.JwtRegister;
import com.cafemanagementapp.jwt.model.JwtRequest;
import com.cafemanagementapp.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<String> signup(JwtRegister requestMap);
    ResponseEntity<?> login(JwtRequest requestMap);

    ResponseEntity<List<UserWrapper>> getAllUsers();

//    ResponseEntity<String> activateUser(Map<String, String> request);

    ResponseEntity<String> enableUser(UserActivateRequest request);

    ResponseEntity<String> checkToken();

    ResponseEntity<String> changePassword(PasswordChangeRequest request);

    ResponseEntity<String> forgotPassword(ForgotPasswordRequest request);
}
