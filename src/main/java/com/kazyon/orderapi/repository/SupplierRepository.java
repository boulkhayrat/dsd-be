package com.kazyon.orderapi.repository;

import com.kazyon.orderapi.model.Supplier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
    List<Supplier> findAllByOrderByCreatedAtDesc();
    List<Supplier> findByNameContainingIgnoreCaseOrderByCreatedAt(String name);
    List<Supplier> findByCode(int code);
}
