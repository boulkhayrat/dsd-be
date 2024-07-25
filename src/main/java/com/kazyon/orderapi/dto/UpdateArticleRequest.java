package com.kazyon.orderapi.dto;


import lombok.Data;

@Data
public class UpdateArticleRequest {
    private int code;
    private Long supplier_id;
    private String unitOfMeasure;
    private String name;
}
