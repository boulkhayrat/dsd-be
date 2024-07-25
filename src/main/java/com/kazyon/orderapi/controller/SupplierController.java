package com.kazyon.orderapi.controller;

import com.kazyon.orderapi.mapper.SupplierMapper;
import com.kazyon.orderapi.model.Supplier;
import com.kazyon.orderapi.model.User;
import com.kazyon.orderapi.dto.CreateSupplierRequest;
import com.kazyon.orderapi.dto.SupplierDto;
import com.kazyon.orderapi.dto.UpdateSupplierRequest;
import com.kazyon.orderapi.security.CustomUserDetails;
import com.kazyon.orderapi.service.SupplierService;
import com.kazyon.orderapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final UserService userService;
    private final SupplierService supplierService;
    private final SupplierMapper supplierMapper;

    @GetMapping
    public List<SupplierDto> getSuppliers(@RequestParam(value = "text", required = false) String text) {
        List<Supplier> suppliers = (text == null) ? supplierService.getSuppliers() : supplierService.searchSuppliers(text);
        return suppliers.stream()
                .map(supplierMapper::toSupplierDto)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SupplierDto createSupplier(@AuthenticationPrincipal CustomUserDetails currentUser,
                                    @Valid @RequestBody CreateSupplierRequest createSupplierRequest) {
        User user = userService.validateAndGetUserByUsername(currentUser.getUsername());
        Supplier supplier = supplierMapper.toSupplier(createSupplierRequest);
        supplier.setUser(user);
        return supplierMapper.toSupplierDto(supplierService.saveSupplier(supplier));
    }

    @DeleteMapping("/{id}")
    public SupplierDto deleteSuppliers(@PathVariable Long id) {
        Supplier supplier = supplierService.validateAndGetSupplier(id);
        supplierService.deleteSupplier(supplier);
        return supplierMapper.toSupplierDto(supplier);
    }

    @PutMapping("/{supplierId}")
    @PreAuthorize("hasRole('ADMIN')")
    public SupplierDto updateSupplier(@AuthenticationPrincipal CustomUserDetails currentUser,
                                    @PathVariable Long supplierId,
                                    @Valid @RequestBody UpdateSupplierRequest updateSupplierRequest) {
        userService.validateAndGetUserByUsername(currentUser.getUsername());
        Supplier updatedSupplier = supplierService.updateSupplier(supplierId, updateSupplierRequest);
        return supplierMapper.toSupplierDto(updatedSupplier);
    }

    @GetMapping("/search")
    public List<SupplierDto> searchSuppliers(@RequestParam(required = false) String text) {
        List<Supplier> suppliers = supplierService.searchSuppliers(text);
        return suppliers.stream()
                .map(supplierMapper::toSupplierDto)
                .collect(Collectors.toList());
    }
}
