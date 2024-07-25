package com.kazyon.orderapi.controller;



import com.kazyon.orderapi.model.VendorSap;
import com.kazyon.orderapi.service.VendorSapService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/vendorsSap")
public class VendorSapController {

    private VendorSapService vendorSapService;


    //    @PostMapping("/upload-vendorsSap-data")
//    public ResponseEntity<?> uploadVendorSapData(@RequestParam("file") MultipartFile file){
//        this.vendorSapService.saveVendorsSapToDataBase(file);
//        return ResponseEntity
//                .ok(Map.of("Message","VendorsSap data uploaded and saved to database with success"));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<VendorSap>> getVendorsSap(){
//        return new ResponseEntity<>(vendorSapService.getVendorsSap(), HttpStatus.FOUND);
//    }
    @GetMapping
    public List<VendorSap> getVendorsSap() {
        return vendorSapService.getVendorsSap();
    }



}
