package com.kazyon.orderapi.mapper;

import com.kazyon.orderapi.dto.ArticleDto;
import com.kazyon.orderapi.dto.CreateArticleRequest;
import com.kazyon.orderapi.model.Article;


public interface ArticleMapper {

    Article toArticle(CreateArticleRequest createArticleRequest);
    ArticleDto toArticleDto(Article article);

}
