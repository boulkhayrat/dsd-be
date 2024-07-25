package com.kazyon.orderapi.dto;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public record OrderDto(Long id, String reference, String note, Date deliveryDate, Boolean valid, ZonedDateTime validationDate, OrderDto.SupplierDto supplier, OrderDto.StoreDto store, OrderDto.UserDto user,
                       List<OrderLineDto> orderLines,
                       ZonedDateTime createdAt) {



    public record UserDto(String username) {
    }
    
    public record SupplierDto(Long id, int code,String name){
        
    }
    public record StoreDto(String name){

    }
    public record OrderLineDto(Long id,String articleName,Long articleId, int articleCode, Long orderId, String orderReference , float quantity) {

    }

}