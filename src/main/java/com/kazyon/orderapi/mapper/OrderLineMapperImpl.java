package com.kazyon.orderapi.mapper;

import com.kazyon.orderapi.dto.CreateOrderLineRequest;
import com.kazyon.orderapi.dto.OrderLineDto;
import com.kazyon.orderapi.model.*;
import com.kazyon.orderapi.model.OrderLine;
import com.kazyon.orderapi.service.ArticleService;
import com.kazyon.orderapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapperImpl implements OrderLineMapper {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private OrderService orderService;

    @Override
    public OrderLine toOrderLine(CreateOrderLineRequest createOrderLineRequest) {
        if (createOrderLineRequest == null) {
            return null;
        }

        OrderLine orderLine =  new OrderLine();
        orderLine.setQuantity(createOrderLineRequest.getQuantity());


        Long articleId = createOrderLineRequest.getArticle_id();
        if (articleId != null) {
            Article article = articleService.validateAndGetArticle(articleId);
            orderLine.setArticle(article);
        }

        Long orderId = createOrderLineRequest.getOrder_id();
        if (orderId != null) {
            Order order = orderService.validateAndGetOrder(orderId);
            orderLine.setOrder(order);
        }

        return orderLine;
    }

    @Override
    public OrderLineDto toOrderLineDto(OrderLine orderLine) {
        if (orderLine == null) {
            return null;
        }
        OrderLineDto.UserDto userDto = null;
        if (orderLine.getUser() != null) {
            userDto = new OrderLineDto.UserDto(orderLine.getUser().getUsername());
        }

        OrderLineDto.OrderDto orderDto = null;
        if (orderLine.getOrder() != null) {
            orderDto = new OrderLineDto.OrderDto(orderLine.getOrder().getReference());
        }

        OrderLineDto.ArticleDto articleDto = null;
        if (orderLine.getArticle() != null) {
            articleDto = new OrderLineDto.ArticleDto(orderLine.getArticle().getName(), orderLine.getArticle().getCode());
        }


        return new OrderLineDto(orderLine.getId(), orderLine.getQuantity(),userDto,orderDto,articleDto,orderLine.getCreatedAt());
    }
}
