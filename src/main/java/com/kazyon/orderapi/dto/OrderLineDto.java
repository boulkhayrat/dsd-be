package com.kazyon.orderapi.dto;

import java.time.ZonedDateTime;

public record OrderLineDto(Long id, float Quantity, OrderLineDto.UserDto user, OrderLineDto.OrderDto order, OrderLineDto.ArticleDto article, ZonedDateTime createdAt  ) {

    public record UserDto(String username) {
    }

    public record OrderDto(String reference) {
    }

    public record ArticleDto(String name,int code) {
    }
}
