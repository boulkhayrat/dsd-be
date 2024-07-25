package com.kazyon.orderapi.mapper;

import com.kazyon.orderapi.dto.ArticleDto;
import com.kazyon.orderapi.dto.CreateArticleRequest;
import com.kazyon.orderapi.dto.OrderDto;
import com.kazyon.orderapi.model.Article;
import com.kazyon.orderapi.model.OrderLine;
import com.kazyon.orderapi.model.Supplier;
import com.kazyon.orderapi.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleMapperImpl implements ArticleMapper{
    @Autowired
    private SupplierService supplierService;

    @Override
    public Article toArticle(CreateArticleRequest createArticleRequest) {
        if (createArticleRequest == null) {
            return null;
        }

        Article article =  new Article();
        article.setName(createArticleRequest.getName());
        article.setCode(createArticleRequest.getCode());
        article.setUnitOfMeasure(createArticleRequest.getUnitOfMeasure());

        // Fetch the Supplier entity by its ID and set it in the Article
        Long supplierId = createArticleRequest.getSupplier_id();
        if (supplierId != null) {
            Supplier supplier = supplierService.validateAndGetSupplier(supplierId);
            article.setSupplier(supplier);
        }

        return article;


    }




    @Override
    public ArticleDto toArticleDto(Article article) {
        if (article == null) {
            return null;
        }
        ArticleDto.UserDto userDto = null;
        if (article.getUser() != null) {
            userDto = new ArticleDto.UserDto(article.getUser().getUsername());
        }

        ArticleDto.SupplierDto supplierDto = null;
        if (article.getSupplier() != null) {
            supplierDto = new ArticleDto.SupplierDto(article.getSupplier().getName());
        }

//        List<ArticleDto.OrderLineDto> orderLinesDto = null;
//        if (article.getOrderLines() != null) {
//            orderLinesDto = article.getOrderLines().stream()
//                            .map(this::toOrderLineDto)
//                            .collect(Collectors.toList());
//        }


        return new ArticleDto(article.getId(), article.getCode(),article.getName(),article.getUnitOfMeasure(), supplierDto, userDto,article.getCreatedAt());
    }

//    private ArticleDto.OrderLineDto toOrderLineDto(OrderLine orderLine) {
//        if (orderLine == null) {
//            return null;
//        }
//
//        return new ArticleDto.OrderLineDto(
//
//                orderLine.getArticle().getId(),
//                orderLine.getOrder().getId(),
//                orderLine.getQuantity()
//        );
//    }
}
