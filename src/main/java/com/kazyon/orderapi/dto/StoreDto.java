package com.kazyon.orderapi.dto;

import java.time.ZonedDateTime;

public record StoreDto(Long id, String code, String city, String name, String address, StoreDto.UserDto user,
                       ZonedDateTime createdAt) {
    public record UserDto(String username){

    }

}
