package com.kazyon.orderapi.controller;

import com.kazyon.orderapi.mapper.OrderLineMapper;
import com.kazyon.orderapi.model.OrderLine;
import com.kazyon.orderapi.model.User;
import com.kazyon.orderapi.dto.CreateOrderLineRequest;
import com.kazyon.orderapi.dto.OrderLineDto;
import com.kazyon.orderapi.dto.UpdateOrderLineRequest;
import com.kazyon.orderapi.security.CustomUserDetails;
import com.kazyon.orderapi.service.OrderLineService;
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
@RequestMapping("/api/orderLines")
public class OrderLineController {

    private final UserService userService;
    private final OrderLineService orderLineService;
    private final OrderLineMapper orderLineMapper;

    @GetMapping
    public List<OrderLineDto> getOrderLines(@RequestParam(value = "text", required = false) String text) {
//        List<OrderLine> orderLines = (text == null) ? orderLineService.getOrderLines() : orderLineService.searchOrderLines(text);
        List<OrderLine> orderLines = orderLineService.getOrderLines();
        return orderLines.stream()
                .map(orderLineMapper::toOrderLineDto)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderLineDto createOrderLine(@AuthenticationPrincipal CustomUserDetails currentUser,
                                    @Valid @RequestBody CreateOrderLineRequest createOrderLineRequest) {

        System.out.println("createOrderLineRequest" + createOrderLineRequest);
        User user = userService.validateAndGetUserByUsername(currentUser.getUsername());
        OrderLine orderLine = orderLineMapper.toOrderLine(createOrderLineRequest);
        orderLine.setUser(user);
        return orderLineMapper.toOrderLineDto(orderLineService.saveOrderLine(orderLine));
    }

    @DeleteMapping("/{id}")
    public OrderLineDto deleteOrderLines(@PathVariable Long id) {
        OrderLine orderLine = orderLineService.validateAndGetOrderLine(id);
        orderLineService.deleteOrderLine(orderLine);
        return orderLineMapper.toOrderLineDto(orderLine);
    }

    @PutMapping("/{orderLineId}")
    //@PreAuthorize("hasRole('ADMIN')")
    public OrderLineDto updateOrderLine(@AuthenticationPrincipal CustomUserDetails currentUser,
                                    @PathVariable Long orderLineId,
                                    @Valid @RequestBody UpdateOrderLineRequest updateOrderLineRequest) {
        System.out.println(updateOrderLineRequest);
        userService.validateAndGetUserByUsername(currentUser.getUsername());
        OrderLine updatedOrderLine = orderLineService.updateOrderLine(orderLineId, updateOrderLineRequest);
        return orderLineMapper.toOrderLineDto(updatedOrderLine);
    }

//    @GetMapping("/search")
//    public List<OrderLineDto> searchOrderLines(@RequestParam(required = false) String text) {
//        List<OrderLine> orderLines = orderLineService.searchOrderLines(text);
//        return orderLines.stream()
//                .map(orderLineMapper::toOrderLineDto)
//                .collect(Collectors.toList());
//    }
}
