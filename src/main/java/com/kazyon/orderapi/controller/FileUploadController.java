package com.kazyon.orderapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/upload")
public class FileUploadController {
    private static final String UPLOAD_DIR = "src/main/resources/public/"; // Web-accessible directory
    private static final String BASE_URL = "http://localhost:8080/public/"; // Update with your actual server address


    @PostMapping("/pdf")
    public ResponseEntity<Map<String, String>> uploadPDF(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            if (!file.getContentType().equals("application/pdf")) {
                response.put("message", "Uploaded file is not a PDF");
                return ResponseEntity.badRequest().body(response);
            }

            // Save the file to the server
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_DIR + "/" + file.getOriginalFilename());
            Files.write(path, bytes);

            // Return the file URL
            String fileUrl = BASE_URL + file.getOriginalFilename();
            response.put("fileUrl", fileUrl);
            response.put("message", "File uploaded successfully: " + file.getOriginalFilename());
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            response.put("message", "Failed to upload file");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
