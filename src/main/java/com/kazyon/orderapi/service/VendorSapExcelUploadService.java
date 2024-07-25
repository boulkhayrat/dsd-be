package com.kazyon.orderapi.service;

import com.kazyon.orderapi.model.VendorSap;
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

@Service
public class VendorSapExcelUploadService {


    @Autowired
    private  ArticleSapRepository articleSapRepository;



    public static boolean isValidExcelFile(Path filePath){
        String fileName = filePath.getFileName().toString().toLowerCase();
        return fileName.endsWith(".xlsx");
    }

    public  List<VendorSap> getVendorsDataFromExcelFile(Path filePath) {
        List<VendorSap> vendorsSap = new ArrayList<>();

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
                VendorSap vendorSap = new VendorSap();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    System.out.println(cell);
                    switch (cellIndex) {
                        case 0 -> vendorSap.setCode(Integer.valueOf(cell.getStringCellValue()));
                        case 1 -> vendorSap.setName(cell.getStringCellValue());
                        case 2 -> vendorSap.setAddress(cell.getStringCellValue());
                        case 5 -> vendorSap.setCity(cell.getStringCellValue());
                        case 4 -> vendorSap.setPostalCode(cell.getStringCellValue());
                        default -> {
                        }
                    }
                    cellIndex++;
                }
                // Check if the vendor is associated with any ArticleSap
                System.out.println("vendor Code :"+vendorSap.getCode());
                if (articleSapRepository.existsByVendorCode(vendorSap.getCode())) {
                    vendorsSap.add(vendorSap);
                }
                rowIndex++;

                System.out.println(rowIndex);
                System.out.println(vendorsSap);
            }
            System.out.println("finishing the process of upload");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return vendorsSap;
    }
}
