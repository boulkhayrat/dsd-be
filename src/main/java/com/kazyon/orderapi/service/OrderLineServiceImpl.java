package com.kazyon.orderapi.service;

import com.kazyon.orderapi.dto.UpdateOrderLineRequest;
import com.kazyon.orderapi.exception.ResourceNotFoundException;
import com.kazyon.orderapi.model.Article;
import com.kazyon.orderapi.model.Order;
import com.kazyon.orderapi.model.OrderLine;
import com.kazyon.orderapi.repository.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderLineServiceImpl implements OrderLineService{

    private final OrderLineRepository orderLineRepository;
    private final ArticleService articleService;
    private final OrderService orderService;

    @Override
    public List<OrderLine> getOrderLines() {
        return orderLineRepository.findAllByOrderByCreatedAtDesc();
    }



    @Override
    public OrderLine validateAndGetOrderLine(Long id) {
        return orderLineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("OrderLine with id %s not found", id)));
    }

    @Override
    public OrderLine updateOrderLine(Long id, UpdateOrderLineRequest updateOrderLineRequest) {
        OrderLine existingOrderLine = validateAndGetOrderLine(id);
        existingOrderLine.setQuantity(updateOrderLineRequest.getQuantity());

        Article article = articleService.validateAndGetArticle(updateOrderLineRequest.getArticle_id());
        existingOrderLine.setArticle(article);

        Order order = orderService.validateAndGetOrder(updateOrderLineRequest.getOrder_id());
        existingOrderLine.setOrder(order);

        return orderLineRepository.save(existingOrderLine);
    }



    @Override
    public OrderLine saveOrderLine(OrderLine orderLine) {

        Optional<OrderLine> existingOrderLine = orderLineRepository.findByOrderAndArticle(orderLine.getOrder(), orderLine.getArticle());
        if (existingOrderLine.isPresent()) {
            throw new IllegalArgumentException("This article is already added to the order.");
        }
        return orderLineRepository.save(orderLine);
    }

    @Override
    public void deleteOrderLine(OrderLine orderLine) {
        orderLineRepository.delete(orderLine);

    }


}



