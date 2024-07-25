package com.kazyon.orderapi.service;

import com.kazyon.orderapi.model.ArticleSap;
import com.kazyon.orderapi.model.StoreSap;
import com.kazyon.orderapi.model.VendorSap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledTaskService {

    @Autowired
    private SapArticleExtractionService sapArticleExtractionService;
    @Autowired
    private SapVendorExtractionService sapVendorExtractionService;
    @Autowired
    private SapStoreExtractionService sapStoreExtractionService;
    @Autowired
    private ArticleSapService articleSapService;
    @Autowired
    private VendorSapService vendorSapService;
    @Autowired
    private StoreSapService storeSapService;



    @Scheduled(cron = "0 0 0 ? * MON") // Runs at midnight on Monday
//    @Scheduled(cron = "0 * * * * *") // Runs every minute
    public void runSapAutomationAndImportJob() {
        System.out.println("Starting SAP automation and import job...");

        // Generate the Excel file for articles and vendors
        sapArticleExtractionService.generateExcelFile();
        sapStoreExtractionService.generateExcelFile();;
        sapVendorExtractionService.generateExcelFile();


        // Import the articles and vendors from the generated file
        List<ArticleSap> articles = sapArticleExtractionService.importArticlesFromGeneratedFile();
        List<StoreSap> stores = sapStoreExtractionService.importStoresFromGeneratedFile();
        List<VendorSap> vendors = sapVendorExtractionService.importVendorsFromGeneratedFile();


        // Add code here to save the articles to the database
        articleSapService.saveArticlesSapToDataBase(articles);
        storeSapService.saveStoresSapToDataBase(stores);
        vendorSapService.saveVendorsSapToDataBase(vendors);


        System.out.println("SAP automation and import job completed.");
    }
}
