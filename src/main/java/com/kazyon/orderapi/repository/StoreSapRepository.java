package com.kazyon.orderapi.repository;

import com.kazyon.orderapi.model.ArticleSap;
import com.kazyon.orderapi.model.StoreSap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreSapRepository extends JpaRepository<StoreSap,Long> {
    Optional<StoreSap> findByCode(String code);
}
