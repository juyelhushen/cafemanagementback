package com.cafemanagementapp.service;

import com.cafemanagementapp.rest.DashboardRest;
import com.cafemanagementapp.wrapper.DashboardWrapper;
import org.springframework.http.ResponseEntity;

public interface DashboardService {
    ResponseEntity<DashboardWrapper> getCount();
}
