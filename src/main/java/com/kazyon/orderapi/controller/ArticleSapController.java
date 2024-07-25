package com.kazyon.orderapi.controller;



import com.kazyon.orderapi.model.ArticleSap;
import com.kazyon.orderapi.service.ArticleSapService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/articlesSap")
public class ArticleSapController {

    private ArticleSapService articleSapService;


//    @PostMapping("/upload-articlesSap-data")
//    public ResponseEntity<?> uploadArticleSapData(@RequestParam("file") MultipartFile file){
//        this.articleSapService.saveArticlesSapToDataBase(file);
//        return ResponseEntity
//                .ok(Map.of("Message","ArticlesSap data uploaded and saved to database with success"));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<ArticleSap>> getArticlesSap(){
//        return new ResponseEntity<>(articleSapService.getArticlesSap(), HttpStatus.FOUND);
//    }
    @GetMapping
    public List<ArticleSap> getArticlesSap() {
        return articleSapService.getArticlesSap();
    }



}
