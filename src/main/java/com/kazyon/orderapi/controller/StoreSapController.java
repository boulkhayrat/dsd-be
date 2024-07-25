package com.kazyon.orderapi.controller;



import com.kazyon.orderapi.model.StoreSap;
import com.kazyon.orderapi.model.VendorSap;
import com.kazyon.orderapi.service.StoreSapService;
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
@RequestMapping("/api/storesSap")
public class StoreSapController {

    private StoreSapService storeSapService;


    //    @PostMapping("/upload-vendorsSap-data")
//    public ResponseEntity<?> uploadVendorSapData(@RequestParam("file") MultipartFile file){
//        this.storeSapService.saveStoresSapToDataBase(file);
//        return ResponseEntity
//                .ok(Map.of("Message","StoresSap data uploaded and saved to database with success"));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<VendorSap>> getStoresSap(){
//        return new ResponseEntity<>(storeSapService.getStoresSap(), HttpStatus.FOUND);
//    }
    @GetMapping
    public List<StoreSap> getStoresSap() {
        return storeSapService.getStoresSap();
    }



}
