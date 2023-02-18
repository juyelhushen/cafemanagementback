package com.cafemanagementapp.service;

import com.cafemanagementapp.wrapper.BillWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BillService {
    ResponseEntity<String> generateReport(Map<String, Object> request);

    ResponseEntity<List<BillWrapper>> getBill();

    ResponseEntity<byte[]> getPDF(Map<String, Object> request);

    ResponseEntity<String> deleteBill(Integer id);
}
