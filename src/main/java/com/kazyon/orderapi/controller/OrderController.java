package com.kazyon.orderapi.controller;

import com.kazyon.orderapi.mapper.OrderMapper;
import com.kazyon.orderapi.model.Order;
import com.kazyon.orderapi.model.User;
import com.kazyon.orderapi.dto.CreateOrderRequest;
import com.kazyon.orderapi.dto.OrderDto;
import com.kazyon.orderapi.dto.UpdateOrderRequest;
import com.kazyon.orderapi.security.CustomUserDetails;
import com.kazyon.orderapi.service.OrderService;
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
@RequestMapping("/api/orders")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;
    private final OrderMapper orderMapper;

//    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    public List<OrderDto> getOrders(@RequestParam(value = "text", required = false) String text) {
        List<Order> orders = (text == null) ? orderService.getOrders() : orderService.getOrdersContainingText(text);
        return orders.stream()
                .map(orderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

//    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderDto createOrder(@AuthenticationPrincipal CustomUserDetails currentUser,
                                @Valid @RequestBody CreateOrderRequest createOrderRequest) {
        User user = userService.validateAndGetUserByUsername(currentUser.getUsername());
        Order order = orderMapper.toOrder(createOrderRequest);
        System.out.println("Order : "+order);


        order.setUser(user);
        return orderMapper.toOrderDto(orderService.saveOrder(order));
    }

//    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @DeleteMapping("/{id}")
    public OrderDto deleteOrders(@PathVariable Long id) {
        Order order = orderService.validateAndGetOrder(id);
        orderService.deleteOrder(order);
        return orderMapper.toOrderDto(order);
    }

    @PutMapping("/{orderId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    public OrderDto updateOrder(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                @PathVariable Long orderId,
                                                @Valid @RequestBody UpdateOrderRequest updateOrderRequest) {
        userService.validateAndGetUserByUsername(currentUser.getUsername());
        System.out.println(updateOrderRequest);
        Order updatedOrder = orderService.updateOrder(orderId, updateOrderRequest);


        return orderMapper.toOrderDto(updatedOrder);
    }

    @GetMapping("/search")
    public List<OrderDto> searchOrders(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String reference
           ) {
        List<Order> orders = orderService.searchOrders(description, reference);
       return orders.stream()
                .map(orderMapper::toOrderDto)
                .collect(Collectors.toList());
    }
}
