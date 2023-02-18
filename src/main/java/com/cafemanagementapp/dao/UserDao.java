package com.cafemanagementapp.dao;

import com.cafemanagementapp.entity.User;
import com.cafemanagementapp.wrapper.UserWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    //    @Query(value = "SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

    List<UserWrapper> getUsers();

    @Transactional
    @Modifying
    void enableUser(@Param("id") Integer id, @Param("enable") Boolean enable);

    List<String> getAllAdmin();

    User findUserByEmail(String email);


//    @Transactional
//    @Modifying
//    Integer updateUser(@Param("enable") Boolean enable,@Param("id") Integer id);
//    }
}
