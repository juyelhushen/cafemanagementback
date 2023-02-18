package com.cafemanagementapp.rest;

import com.cafemanagementapp.wrapper.ForgotPasswordRequest;
import com.cafemanagementapp.wrapper.PasswordChangeRequest;
import com.cafemanagementapp.wrapper.UserActivateRequest;
import com.cafemanagementapp.jwt.model.JwtRegister;
import com.cafemanagementapp.jwt.model.JwtRequest;
import com.cafemanagementapp.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
public interface UserRest {

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody(required = true) JwtRegister request);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody(required = true) JwtRequest request);

    @GetMapping("/get")
    public ResponseEntity<List<UserWrapper>> getAllUsers();

    @PostMapping("/update")
    public ResponseEntity<String> enableUser(@RequestBody UserActivateRequest request);

    @GetMapping("/test")
    public ResponseEntity<String> testUser();
    @GetMapping("/checktoken")
    public ResponseEntity<String> checkToken();

    @PostMapping("/changepassword")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest request);

    @PostMapping("/forgotpassword")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request);


}