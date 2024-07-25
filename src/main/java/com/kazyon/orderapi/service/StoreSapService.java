package com.kazyon.orderapi.service;

import com.kazyon.orderapi.model.StoreSap;
import com.kazyon.orderapi.model.VendorSap;
import com.kazyon.orderapi.repository.StoreSapRepository;
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
public class StoreSapService {

    private static final Logger logger = LoggerFactory.getLogger(StoreSapService.class);
    @Autowired
    private StoreSapRepository storeSapRepository;

    @Transactional
    public void saveStoresSapToDataBase(List<StoreSap> storesSap) {
        logger.info("Starting to save stores one by one");

        for (StoreSap store : storesSap) {
            try {
                storeSapRepository.save(store);
                logger.info("Saved store: {}", store);
            } catch (DataIntegrityViolationException e) {
                // Handle unique constraint violation (if needed)
                logger.warn("Unique constraint violation for store: {}", store);
                // Optionally, you can continue or take specific action here
            } catch (Exception e) {
                // Log other exceptions
                logger.error("Error saving store: {}", store, e);
            }
        }

        logger.info("Finished saving stores");
    }

    public List<StoreSap> getStoresSap() {
        return storeSapRepository.findAll();
    }
}
