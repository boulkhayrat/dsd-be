package com.kazyon.orderapi.dto;


import lombok.Data;

@Data
public class UpdateSupplierRequest {


    private int code;
    private String name;
    private String address;
    private Integer fiscalIds;
}
