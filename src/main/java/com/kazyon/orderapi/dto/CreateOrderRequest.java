package com.kazyon.orderapi.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class CreateOrderRequest {
    private boolean valid;
    private String reference;
    private String note;
    private Long supplier_id;
    private Date deliveryDate;


}
