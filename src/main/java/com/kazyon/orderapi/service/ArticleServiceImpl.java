package com.kazyon.orderapi.service;

import com.kazyon.orderapi.dto.UpdateArticleRequest;
import com.kazyon.orderapi.exception.ResourceNotFoundException;
import com.kazyon.orderapi.model.Article;
import com.kazyon.orderapi.model.Supplier;
import com.kazyon.orderapi.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;
    private final SupplierServiceImpl supplierService;

    @Override
    public List<Article> getArticles() {
        return articleRepository.findAllByOrderByCreatedAtDesc();
    }



    @Override
    public Article validateAndGetArticle(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Article with id %s not found", id)));
    }

    @Override
    public Article updateArticle(Long id, UpdateArticleRequest updateArticleRequest) {
        Article existingArticle = validateAndGetArticle(id);
        existingArticle.setName(updateArticleRequest.getName());
        existingArticle.setUnitOfMeasure(updateArticleRequest.getUnitOfMeasure());
        existingArticle.setCode(updateArticleRequest.getCode());
        Supplier supplier = supplierService.validateAndGetSupplier(updateArticleRequest.getSupplier_id());
        existingArticle.setSupplier(supplier);

        return articleRepository.save(existingArticle);
    }



    @Override
    public Article saveArticle(Article article) {

        return articleRepository.save(article);
    }

    @Override
    public void deleteArticle(Article article) {
        articleRepository.delete(article);

    }

    @Override
    public List<Article> searchArticles(String text) {
        if (isNumeric(text)) {
            int code = Integer.parseInt(text);
            return articleRepository.findByCode(code);
        } else {
            return articleRepository.findByUnitOfMeasureContainingIgnoreCaseOrNameContainingIgnoreCaseOrderByCreatedAt(text, text);
        }
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
