package com.kazyon.orderapi.controller;

import com.kazyon.orderapi.service.ArticleService;
import com.kazyon.orderapi.service.OrderService;
import com.kazyon.orderapi.service.SupplierService;
import com.kazyon.orderapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/static/public")
public class PublicController {

    private final UserService userService;
    private final OrderService orderService;
    private final SupplierService supplierService;
    private final ArticleService articleService;

    @GetMapping("/numberOfUsers")
    public Integer getNumberOfUsers() {
        return userService.getUsers().size();
    }

    @GetMapping("/numberOfOrders")
    public Integer getNumberOfOrders() {
        return orderService.getOrders().size();
    }

    @GetMapping("/numberOfSuppliers")
    public Integer getNumberOfSuppliers() {
        return supplierService.getSuppliers().size();
    }

    @GetMapping("/numberOfArticles")
    public Integer getNumberOfArticles() {
        return articleService.getArticles().size();
    }
}
