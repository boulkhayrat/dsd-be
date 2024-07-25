package com.kazyon.orderapi.service;

import com.kazyon.orderapi.dto.UpdateArticleRequest;
import com.kazyon.orderapi.dto.UpdateSupplierRequest;
import com.kazyon.orderapi.exception.ResourceNotFoundException;
import com.kazyon.orderapi.model.Article;
import com.kazyon.orderapi.model.Supplier;
import com.kazyon.orderapi.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService{

    private final SupplierRepository supplierRepository;

    @Override
    public List<Supplier> getSuppliers() {
        return supplierRepository.findAllByOrderByCreatedAtDesc();
    }


    @Override
    public Supplier validateAndGetSupplier(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Supplier with id %s not found", id)));
    }


    @Override
    public Supplier updateSupplier(Long id, UpdateSupplierRequest updateSupplierRequest) {

        Supplier existingSupplier = validateAndGetSupplier(id);
        existingSupplier.setName(updateSupplierRequest.getName());
        existingSupplier.setCode(updateSupplierRequest.getCode());
        existingSupplier.setAddress(updateSupplierRequest.getAddress());
        existingSupplier.setFiscalIds(updateSupplierRequest.getFiscalIds());


        return supplierRepository.save(existingSupplier);
    }

    @Override
    public Supplier saveSupplier(Supplier supplier) {

        return supplierRepository.save(supplier);
    }

    @Override
    public void deleteSupplier(Supplier supplier) {
        supplierRepository.delete(supplier);

    }

    @Override
    public List<Supplier> searchSuppliers(String text) {
        if (isNumeric(text)) {
            int code = Integer.parseInt(text);
            return supplierRepository.findByCode(code);
        } else {
            return supplierRepository.findByNameContainingIgnoreCaseOrderByCreatedAt(text);
        }
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
