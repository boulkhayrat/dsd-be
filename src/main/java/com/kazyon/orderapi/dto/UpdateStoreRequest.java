package com.kazyon.orderapi.dto;

import lombok.Data;

@Data
public class UpdateStoreRequest {
    private String code;
    private String name;
    private String address;
    private String city;
    private String busArea;
}
