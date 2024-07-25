package com.kazyon.orderapi.repository;


import com.kazyon.orderapi.model.ArticleSap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleSapRepository extends JpaRepository<ArticleSap, Long> {
    boolean existsByVendorCode(int vendorCode);

    Optional<ArticleSap> findByCode(int code);
}
