package com.kazyon.orderapi.service;

import com.kazyon.orderapi.model.VendorSap;
import com.kazyon.orderapi.model.VendorSap;
import com.kazyon.orderapi.repository.VendorSapRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class VendorSapService {

    private static final Logger logger = LoggerFactory.getLogger(VendorSapService.class);
    @Autowired
    private VendorSapRepository vendorSapRepository;

    @Transactional
    public void saveVendorsSapToDataBase(List<VendorSap> vendorsSap) {
        logger.info("Starting to save vendors one by one");

        for (VendorSap vendor : vendorsSap) {
            try {
                vendorSapRepository.save(vendor);
                logger.info("Saved vendor: {}", vendor);
            } catch (DataIntegrityViolationException e) {
                // Handle unique constraint violation (if needed)
                logger.warn("Unique constraint violation for vendor: {}", vendor);
                // Optionally, you can continue or take specific action here
            } catch (Exception e) {
                // Log other exceptions
                logger.error("Error saving vendor: {}", vendor, e);
            }
        }

        logger.info("Finished saving vendors");
    }

    public List<VendorSap> getVendorsSap() {
        return vendorSapRepository.findAll();
    }
}
