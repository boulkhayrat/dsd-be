package com.kazyon.orderapi.dto;

import com.kazyon.orderapi.model.Supplier;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public record UserDto(Long id, String username, String name, String email, String role,UserDto.StoreDto store, List<OrderDto> orders) {

    public record OrderDto(Long id, String note, String reference, Date deliveryDate, boolean valid, List<OrderDto.OrderLineDto> orderLines, OrderDto.SupplierDto supplier, ZonedDateTime createdAt) {
        public record  SupplierDto(String name){

        }
        public record OrderLineDto(Long id, String articleName,Long articleId, Long orderId, int articleCode, String orderReference , float quantity) {

        }


    }
    public record StoreDto(Long id, String name, String code, String address, String city, String BusArea ){

    }

}