package com.kazyon.orderapi.mapper;

import com.kazyon.orderapi.dto.ArticleDto;
import com.kazyon.orderapi.dto.CreateArticleRequest;
import com.kazyon.orderapi.dto.CreateOrderLineRequest;
import com.kazyon.orderapi.dto.OrderLineDto;
import com.kazyon.orderapi.model.Article;
import com.kazyon.orderapi.model.OrderLine;

public interface OrderLineMapper {
    OrderLine toOrderLine(CreateOrderLineRequest createOrderLineRequest);
    OrderLineDto toOrderLineDto(OrderLine orderLine);
}
