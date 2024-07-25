package com.kazyon.orderapi.service;

import com.kazyon.orderapi.dto.UpdateArticleRequest;
import com.kazyon.orderapi.model.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {

    List<Article> getArticles();


    Article validateAndGetArticle(Long id);

    Article updateArticle(Long id, UpdateArticleRequest updateArticleRequest);
    Article saveArticle(Article article);

    void deleteArticle(Article article);
    List<Article> searchArticles(String text);
}
