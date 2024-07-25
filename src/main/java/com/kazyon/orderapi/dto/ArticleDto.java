package com.kazyon.orderapi.dto;

import java.time.ZonedDateTime;
import java.util.List;

public record ArticleDto(Long id, int code, String name, String unitOfMeasure, ArticleDto.SupplierDto supplier, ArticleDto.UserDto user,

                         ZonedDateTime createdAt) {
    public record UserDto(String username) {
    }
    public record SupplierDto(String name) {
    }

}
