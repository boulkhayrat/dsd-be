package com.kazyon.orderapi.dto;

import lombok.Data;

@Data
public class UpdateOrderLineRequest {
    private Long order_id;
    private Long article_id;
    private float quantity;

}
