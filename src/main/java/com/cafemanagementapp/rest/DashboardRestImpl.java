package com.cafemanagementapp.rest;

import com.cafemanagementapp.service.DashboardService;
import com.cafemanagementapp.wrapper.DashboardWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DashboardRestImpl implements DashboardRest{

    @Autowired
    private final DashboardService service;

    @Override
    public ResponseEntity<DashboardWrapper> getCount() {
        return service.getCount();
    }
}
