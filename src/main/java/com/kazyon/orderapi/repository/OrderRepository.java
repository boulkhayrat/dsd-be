package com.kazyon.orderapi.repository;

import com.kazyon.orderapi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByOrderByCreatedAtDesc();


    List<Order> findByNoteContainingIgnoreCaseOrderByCreatedAt(String description);

//    @Query("SELECT o FROM Order o WHERE " +
//            "LOWER(o.description) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
//            "LOWER(o.reference) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
//            "LOWER(o.statut) = LOWER(:status)")
//    List<Order> searchOrders(@Param("searchText") String searchText, @Param("status") String status);

    List<Order> findByNoteContainingIgnoreCaseOrReferenceContainingIgnoreCaseOrderByCreatedAt(
            String description, String reference);
    Optional<Order> findTopByOrderByReferenceDesc();
}
