package com.kazyon.orderapi.service;

import com.kazyon.orderapi.model.StoreSap;
import com.kazyon.orderapi.repository.StoreSapRepository;
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
public class StoreSapExcelUploadService {

    @Autowired
    private  StoreSapRepository storeSapRepository;

    public static boolean isValidExcelFile(Path filePath){
        String fileName = filePath.getFileName().toString().toLowerCase();
        return fileName.endsWith(".xlsx");
    }

    public  List<StoreSap> getStoresDataFromExcelFile(Path filePath) {
        List<StoreSap> storesSap = new ArrayList<>();

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
                StoreSap storeSap = new StoreSap();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    System.out.println(cell);
                    switch (cellIndex) {
                        case 0 -> storeSap.setBusArea(cell.getStringCellValue());
                        case 2 -> storeSap.setCode(cell.getStringCellValue());
                        case 3 -> storeSap.setName(cell.getStringCellValue());
                        case 6 -> storeSap.setAddress(cell.getStringCellValue());
                        case 8 -> storeSap.setCity(cell.getStringCellValue());
                        default -> {
                        }
                    }
                    cellIndex++;
                }

                // Check if the store with the same code already exists in the database
                Optional<StoreSap> existingStore = storeSapRepository.findByCode(storeSap.getCode());
                if (existingStore.isEmpty()) {
                    storesSap.add(storeSap);
                } else {
                    System.out.println("Store with code " + storeSap.getCode() + " already exists in the database. Skipping.");
                }
                rowIndex++;

                System.out.println(rowIndex);
                System.out.println(storesSap);
            }
            System.out.println("finishing the process of upload");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return storesSap;
    }
}
