package com.kazyon.orderapi.service;

import com.kazyon.orderapi.model.Order;
import com.kazyon.orderapi.dto.UpdateOrderRequest;

import java.util.List;

public interface OrderService {

    List<Order> getOrders();

    List<Order> getOrdersContainingText(String text);

    Order validateAndGetOrder(Long id);

    Order updateOrder(Long id, UpdateOrderRequest order);
    Order saveOrder(Order order);

    void deleteOrder(Order order);

//    List<Order> searchOrders(String searchText, String status);

    List<Order> searchOrders(String description, String reference);
}
