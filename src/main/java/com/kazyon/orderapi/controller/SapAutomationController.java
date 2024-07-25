package com.kazyon.orderapi.controller;

import com.kazyon.orderapi.model.ArticleSap;

import com.kazyon.orderapi.service.SapArticleExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SapAutomationController {

    @Autowired
    private SapArticleExtractionService sapArticleExtractionService;

    @GetMapping("/generate-excel")
    public String generateExcel() {
        sapArticleExtractionService.generateExcelFile();
        return "Excel file generated successfully";
    }

    @GetMapping("/import-articles")
    public List<ArticleSap> importArticles() {
        return sapArticleExtractionService.importArticlesFromGeneratedFile();
    }
}
