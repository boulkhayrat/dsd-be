package com.kazyon.orderapi.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;


@Controller
public class FileController {

    // Injecting the upload directory path from application.yml
    @Value("${file.upload-dir}")
    private String uploadDir;


    @GetMapping("/uploads/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws FileNotFoundException {
        System.out.println(uploadDir);
        // Load file as Resource
        Resource resource = new FileSystemResource(uploadDir  + fileName);
        System.out.println(resource);
        // Check if file exists
        if (!resource.exists()) {
            throw new FileNotFoundException("File not found: " + fileName);
        }

        // Determine content type based on file extension or use a default
        String contentType = "application/pdf"; // Modify according to your file type

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
