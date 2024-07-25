package com.kazyon.orderapi.service;

import com.kazyon.orderapi.dto.UpdateOrderLineRequest;
import com.kazyon.orderapi.model.Order;
import com.kazyon.orderapi.dto.UpdateOrderRequest;
import com.kazyon.orderapi.model.OrderLine;

import java.util.List;

public interface OrderLineService {

    List<OrderLine> getOrderLines();


    OrderLine validateAndGetOrderLine(Long id);

    OrderLine updateOrderLine(Long id, UpdateOrderLineRequest orderLine);
    OrderLine saveOrderLine(OrderLine orderLine);

    void deleteOrderLine(OrderLine orderLine);

}
