package com.kazyon.orderapi.service;

import com.kazyon.orderapi.model.ArticleSap;
import com.kazyon.orderapi.model.VendorSap;
import com.kazyon.orderapi.repository.ArticleSapRepository;
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
public class ArticleSapService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleSapService.class);
    @Autowired
    private ArticleSapRepository articleSapRepository;

    @Transactional
    public void saveArticlesSapToDataBase(List<ArticleSap> articlesSap) {
        logger.info("Starting to save articles one by one");

        for (ArticleSap article : articlesSap) {
            try {
                articleSapRepository.save(article);
                logger.info("Saved article: {}", article);
            } catch (DataIntegrityViolationException e) {
                // Handle unique constraint violation (if needed)
                logger.warn("Unique constraint violation for article: {}", article);
                // Optionally, you can continue or take specific action here
            } catch (Exception e) {
                // Log other exceptions
                logger.error("Error saving article: {}", article, e);
            }
        }

        logger.info("Finished saving articles");
    }

    public List<ArticleSap> getArticlesSap() {
        return articleSapRepository.findAll();
    }
}
