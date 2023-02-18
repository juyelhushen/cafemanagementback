package com.cafemanagementapp.rest;

import com.cafemanagementapp.wrapper.DashboardWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/dashboard")
public interface DashboardRest {

    @GetMapping("/details")
    public ResponseEntity<DashboardWrapper> getCount();
}
