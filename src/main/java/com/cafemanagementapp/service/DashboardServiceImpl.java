package com.cafemanagementapp.service;

import com.cafemanagementapp.dao.BillDao;
import com.cafemanagementapp.dao.CategoryDao;
import com.cafemanagementapp.dao.ProductDao;
import com.cafemanagementapp.wrapper.DashboardWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService{

    @Autowired
    private final BillDao billDao;
    private final CategoryDao categoryDao;
    private final ProductDao productDao;

    @Override
    public ResponseEntity<DashboardWrapper> getCount() {
        DashboardWrapper dashboard = new DashboardWrapper();
        dashboard.setCategory((int) categoryDao.count());
        dashboard.setProduct((int) productDao.count());
        dashboard.setBill((int) billDao.count());
        return new ResponseEntity<>(dashboard, HttpStatus.OK);
    }
}
