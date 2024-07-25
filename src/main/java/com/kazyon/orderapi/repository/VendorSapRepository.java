package com.kazyon.orderapi.repository;


import com.kazyon.orderapi.model.VendorSap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorSapRepository extends JpaRepository<VendorSap, Long> {
}
