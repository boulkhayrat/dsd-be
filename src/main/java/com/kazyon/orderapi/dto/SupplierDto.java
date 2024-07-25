package com.kazyon.orderapi.dto;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public record SupplierDto(Long id, int code, String name, String address, Integer fiscalIds, List<OrderDto> orders, List<SupplierDto.ArticleDto> articles, SupplierDto.UserDto user, ZonedDateTime createdAt) {

    public record UserDto (String username){
    }

    public record OrderDto (String reference,String Description, Date deliveryDate, boolean isValidated, StoreDto storeDto){
    }

    public record ArticleDto (Long id, int code, String name, String unitOfMeasure){

    }

}
