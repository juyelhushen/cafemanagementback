package com.cafemanagementapp.rest;


import com.cafemanagementapp.wrapper.BillWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/bill")
public interface BillRest {

    @PostMapping("/generatereport")
    public ResponseEntity<String> generateReport(@RequestBody Map<String, Object> request);

    @GetMapping("/getbills")
    public ResponseEntity<List<BillWrapper>> getBills();

    @PostMapping("/getpdf")
    public ResponseEntity<byte[]> getPDF(@RequestBody Map<String, Object> request);

    @DeleteMapping("/deletebill/{id}")
    public ResponseEntity<String> deleteBill(@PathVariable Integer id);
}
