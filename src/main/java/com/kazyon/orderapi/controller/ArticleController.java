package com.kazyon.orderapi.controller;

import com.kazyon.orderapi.mapper.ArticleMapper;
import com.kazyon.orderapi.model.Article;
import com.kazyon.orderapi.model.User;
import com.kazyon.orderapi.dto.CreateArticleRequest;
import com.kazyon.orderapi.dto.ArticleDto;
import com.kazyon.orderapi.dto.UpdateArticleRequest;
import com.kazyon.orderapi.security.CustomUserDetails;
import com.kazyon.orderapi.service.ArticleService;
import com.kazyon.orderapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final UserService userService;
    private final ArticleService articleService;
    private final ArticleMapper articleMapper;

    @GetMapping
    public List<ArticleDto> getArticles(@RequestParam(value = "text", required = false) String text) {
        List<Article> articles = (text == null) ? articleService.getArticles() : articleService.searchArticles(text);
        return articles.stream()
                .map(articleMapper::toArticleDto)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ArticleDto createArticle(@AuthenticationPrincipal CustomUserDetails currentUser,
                                    @Valid @RequestBody CreateArticleRequest createArticleRequest) {

        System.out.println("createArticleRequest" + createArticleRequest);
        User user = userService.validateAndGetUserByUsername(currentUser.getUsername());
        Article article = articleMapper.toArticle(createArticleRequest);
        article.setUser(user);
        return articleMapper.toArticleDto(articleService.saveArticle(article));
    }

    @DeleteMapping("/{id}")
    public ArticleDto deleteArticles(@PathVariable Long id) {
        Article article = articleService.validateAndGetArticle(id);
        articleService.deleteArticle(article);
        return articleMapper.toArticleDto(article);
    }

    @PutMapping("/{articleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ArticleDto updateArticle(@AuthenticationPrincipal CustomUserDetails currentUser,
                                    @PathVariable Long articleId,
                                    @Valid @RequestBody UpdateArticleRequest updateArticleRequest) {
        System.out.println(updateArticleRequest);
        userService.validateAndGetUserByUsername(currentUser.getUsername());
        Article updatedArticle = articleService.updateArticle(articleId, updateArticleRequest);
        return articleMapper.toArticleDto(updatedArticle);
    }

    @GetMapping("/search")
    public List<ArticleDto> searchArticles(@RequestParam(required = false) String text) {
        List<Article> articles = articleService.searchArticles(text);
        return articles.stream()
                .map(articleMapper::toArticleDto)
                .collect(Collectors.toList());
    }
}
