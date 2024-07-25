package com.kazyon.orderapi.dto;

import com.kazyon.orderapi.model.OrderLine;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Data
public class UpdateOrderRequest {
    private String reference;
    //private List<OrderLine> orderLines;

    private String note;
    private Date deliveryDate;
    private ZonedDateTime validationDate;
    private boolean valid;
    private Long supplier_id;


}
