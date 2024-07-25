package com.kazyon.orderapi.service;

import com.kazyon.orderapi.model.ArticleSap;
import com.kazyon.orderapi.repository.ArticleSapRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleSapExcelUploadService {

    @Autowired
    private  ArticleSapRepository articleSapRepository;

    public static boolean isValidExcelFile(Path filePath){
        String fileName = filePath.getFileName().toString().toLowerCase();
        return fileName.endsWith(".xlsx");
    }

    public  List<ArticleSap> getArticlesDataFromExcelFile(Path filePath) {
        List<ArticleSap> articlesSap = new ArrayList<>();

        try (InputStream inputStream = new FileInputStream(filePath.toFile())) {
            System.out.println("starting the process of uploading data");

            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);  // Use the first sheet

            int rowIndex = 0;
            for (Row row : sheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                ArticleSap articleSap = new ArticleSap();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    System.out.println(cell);
                    switch (cellIndex) {
                        case 0 -> articleSap.setCode(Integer.valueOf(cell.getStringCellValue()));
                        case 1 -> articleSap.setName(cell.getStringCellValue());
                        case 2 -> articleSap.setUnitOfMeasure(cell.getStringCellValue());
                        case 3 -> articleSap.setVendorCode(Integer.valueOf(cell.getStringCellValue()));
                        default -> {
                        }
                    }
                    cellIndex++;
                }

                // Check if the article with the same code already exists in the database
                Optional<ArticleSap> existingArticle = articleSapRepository.findByCode(articleSap.getCode());
                if (existingArticle.isEmpty()) {
                    articlesSap.add(articleSap);
                } else {
                    System.out.println("Article with code " + articleSap.getCode() + " already exists in the database. Skipping.");
                }
                rowIndex++;

                System.out.println(rowIndex);
                System.out.println(articlesSap);
            }
            System.out.println("finishing the process of upload");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return articlesSap;
    }
}
