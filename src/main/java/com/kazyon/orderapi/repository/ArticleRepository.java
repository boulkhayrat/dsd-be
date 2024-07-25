package com.kazyon.orderapi.repository;

import com.kazyon.orderapi.model.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Article> findAllByOrderByCreatedAtDesc();
    List<Article> findByUnitOfMeasureContainingIgnoreCaseOrNameContainingIgnoreCaseOrderByCreatedAt(String unitOfMeasure,String name);
    List<Article> findByCode(int code);
}
