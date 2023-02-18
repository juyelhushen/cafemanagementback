package com.cafemanagementapp.dao;

import com.cafemanagementapp.entity.Bill;
import com.cafemanagementapp.wrapper.BillWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDao extends JpaRepository<Bill, Integer> {
   List<BillWrapper> getAllBill();

   List<BillWrapper> getUserBill(@Param("username") String user);
}
