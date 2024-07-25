package com.kazyon.orderapi.mapper;

import com.kazyon.orderapi.model.Order;
import com.kazyon.orderapi.dto.CreateOrderRequest;
import com.kazyon.orderapi.dto.OrderDto;


public interface OrderMapper {

    Order toOrder(CreateOrderRequest createOrderRequest);
    OrderDto toOrderDto(Order order);
}