package com.cafemanagementapp.jwt;

import com.cafemanagementapp.dao.UserDao;
import com.cafemanagementapp.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("inside loadUserByUsername method");
        User user = userDao.findByEmail(username);
        if (!Objects.isNull(username)) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User Not Found");
        }
    }

}
