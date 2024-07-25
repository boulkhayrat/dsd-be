package com.kazyon.orderapi.repository;

import com.kazyon.orderapi.model.Store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {
    List<Store> findAllByOrderByCreatedAtDesc();
    List<Store> findByAddressContainingIgnoreCaseOrNameContainingIgnoreCaseOrCodeContainingIgnoreCaseOrderByCreatedAt(String address,String name, String code);

}
