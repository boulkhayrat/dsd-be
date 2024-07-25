package com.kazyon.orderapi.service;

import com.kazyon.orderapi.dto.UpdateSupplierRequest;
import com.kazyon.orderapi.model.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> getSuppliers();


    Supplier validateAndGetSupplier(Long id);

    Supplier updateSupplier(Long id, UpdateSupplierRequest updateSupplierRequest);
    Supplier saveSupplier(Supplier supplier);

    void deleteSupplier(Supplier supplier);
    List<Supplier> searchSuppliers(String text);
}
