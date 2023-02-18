package com.cafemanagementapp.service;


import com.cafemanagementapp.constant.CafeConstants;
import com.cafemanagementapp.dao.UserDao;
import com.cafemanagementapp.wrapper.ForgotPasswordRequest;
import com.cafemanagementapp.wrapper.PasswordChangeRequest;
import com.cafemanagementapp.wrapper.UserActivateRequest;
import com.cafemanagementapp.jwt.JwtAuthFilter;
import com.cafemanagementapp.jwt.JwtUtils;
import com.cafemanagementapp.jwt.UserDetailsServiceImpl;
import com.cafemanagementapp.jwt.model.JwtRegister;
import com.cafemanagementapp.jwt.model.JwtRequest;
import com.cafemanagementapp.jwt.model.JwtResponse;
import com.cafemanagementapp.entity.Role;
import com.cafemanagementapp.entity.User;
import com.cafemanagementapp.utils.CafeUtils;
import com.cafemanagementapp.utils.MailUtils;
import com.cafemanagementapp.wrapper.UserWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserDao userDao;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthFilter filter;
    private final MailUtils mailUtils;

    @Override

    public ResponseEntity<String> signup(JwtRegister request) {
        log.info("inside signup {}", request);
        try {
            if (signUpValidate(request)) {
                User user = userDao.findByEmail(request.getEmail());
                if (Objects.isNull(user)) {
                    userDao.save(register(request));
                    return CafeUtils.getResponse("Registered Successfully", HttpStatus.OK);
                } else {
                    return CafeUtils.getResponse("Email Already Exists", HttpStatus.BAD_REQUEST);
                }

            } else {
                return CafeUtils.getResponse(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private User register(JwtRegister register) {
        return User.builder()
                .name(register.getName())
                .contactNumber(register.getContactNumber())
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .role(Role.USER)
                .enable(false)
                .build();
    }

    private boolean signUpValidate(JwtRegister register) {
        return register.getName() != null && register.getEmail() != null
                && register.getContactNumber() != null && register.getPassword() != null;
    }




    @Override
    public ResponseEntity<?> login(JwtRequest request) {
        log.info("inside login");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));
            if (auth.isAuthenticated()) {
                User user = userDao.findByEmail(request.getEmail());
                if (user.isEnable()) {
                    String jwtToken = jwtUtils.generateToken(user.getEmail(), user.getRole());
                    JwtResponse token = JwtResponse.builder().token(jwtToken).build();
                    return new ResponseEntity<>(token, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("{\"message\":\"" + "Wait for admin approval." + "\"}"
                            , HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("{\"message\":\"" + "Bad Credentials.." + "\"}"
                , HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try {
            if (filter.isAdmin()) {
                List<UserWrapper> users = userDao.getUsers();
                return new ResponseEntity<List<UserWrapper>>(users,HttpStatus.OK);
            } else  {
                return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>> (new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> enableUser(UserActivateRequest request) {
        try {
            if (filter.isAdmin()) {
                Optional<User> user = userDao.findById(request.getId());
                if (user.isPresent()) {
                    userDao.enableUser(request.getId(),request.getEnable());
                    sendMailToAllAdmin(request.getEnable(),user.get().getEmail(),userDao.getAllAdmin());
                    return new ResponseEntity<>("User is enabled now.",HttpStatus.OK);
                } else {
                    return CafeUtils.getResponse("User id doesn't exist.",HttpStatus.NOT_FOUND);
                }
            } else {
                return CafeUtils.getResponse(CafeConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendMailToAllAdmin(Boolean enable, String user, List<String> allAdmin) {
        allAdmin.remove(filter.getCurrentUser());
        if(enable != null && enable) {
            mailUtils.messageSender(filter.getCurrentUser(),"Account Approved",
                    "USER:-"+user+" \n is approved by \n ADMIN:-"+filter.getCurrentUser(),allAdmin);
        } else {
            mailUtils.messageSender(filter.getCurrentUser(),"Account Disabled",
                    "USER:-"+user+" \n is disabled by \n ADMIN:-"+filter.getCurrentUser(),allAdmin);
        }
    }



    @Override
    public ResponseEntity<String> checkToken() {
        return CafeUtils.getResponse("true",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(PasswordChangeRequest request) {
        try {
            User userObj = userDao.findUserByEmail(filter.getCurrentUser());
            if(userObj != null) {
                BCryptPasswordEncoder b = new BCryptPasswordEncoder();
                if(b.matches(request.getOldPassword(), userObj.getPassword())) {
                    userObj.setPassword(passwordEncoder.encode(request.getNewPassword()));
                    userDao.save(userObj);
                    return CafeUtils.getResponse("Password has been Changed Successfully",HttpStatus.OK);
                } else {
                    return CafeUtils.getResponse("Incorrect Old Password",HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<String> forgotPassword(ForgotPasswordRequest request) {
        try {
            User user = userDao.findUserByEmail(request.getEmail());
            if (user != null)

                mailUtils.forgetMail(user.getEmail(),"Reset Password for cafe management account",user.getPassword());
            return CafeUtils.getResponse("Check your mail for Credentials.",HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

