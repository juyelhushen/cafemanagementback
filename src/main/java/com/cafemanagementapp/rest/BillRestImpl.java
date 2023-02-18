package com.cafemanagementapp.rest;

import com.cafemanagementapp.constant.CafeConstants;
import com.cafemanagementapp.service.BillServiceImpl;
import com.cafemanagementapp.utils.CafeUtils;
import com.cafemanagementapp.wrapper.BillWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@RestController
@RequiredArgsConstructor
public class BillRestImpl implements BillRest{

    @Autowired
    private final BillServiceImpl service;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> request) {
        try {
            return service.generateReport(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<BillWrapper>> getBills() {
        try {
            return service.getBill();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<byte[]> getPDF(Map<String, Object> request) {
        try {
            return service.getPDF(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        try {
            return service.deleteBill(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
