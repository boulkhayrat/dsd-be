package com.kazyon.orderapi.mapper;


import com.kazyon.orderapi.dto.CreateSupplierRequest;
import com.kazyon.orderapi.dto.SupplierDto;
import com.kazyon.orderapi.model.Supplier;

public interface SupplierMapper {

    Supplier toSupplier(CreateSupplierRequest createSupplierRequest);
    SupplierDto toSupplierDto(Supplier supplier);

}
