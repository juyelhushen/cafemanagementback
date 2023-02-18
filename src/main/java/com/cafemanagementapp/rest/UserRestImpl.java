package com.cafemanagementapp.rest;

import com.cafemanagementapp.constant.CafeConstants;
import com.cafemanagementapp.wrapper.ForgotPasswordRequest;
import com.cafemanagementapp.wrapper.PasswordChangeRequest;
import com.cafemanagementapp.wrapper.UserActivateRequest;
import com.cafemanagementapp.jwt.model.JwtRegister;
import com.cafemanagementapp.jwt.model.JwtRequest;
import com.cafemanagementapp.service.UserService;
import com.cafemanagementapp.utils.CafeUtils;
import com.cafemanagementapp.wrapper.UserWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserRestImpl implements UserRest {

    private final UserService userService;

    @Override
    public ResponseEntity<String> signup(JwtRegister request) {
        try {
            return userService.signup(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> login(JwtRequest request) {
        try {
            return userService.login(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try {
            return userService.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> enableUser(UserActivateRequest request) {
        try {
            return userService.enableUser(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<String> testUser() {
        return new ResponseEntity<>("Testing Ok", HttpStatus.OK);
    }

//    @Override
//    public ResponseEntity<String> activateUser(Map<String, String> request) {
//        try {
//            return userService.activateUser(request);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
//    }


    @Override
    public ResponseEntity<String> checkToken() {
        try {
            return userService.checkToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(PasswordChangeRequest request) {
        try {
            return userService.changePassword(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(ForgotPasswordRequest request) {
        try {
            return userService.forgotPassword(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
