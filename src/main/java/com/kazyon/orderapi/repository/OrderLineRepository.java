package com.kazyon.orderapi.repository;


import com.kazyon.orderapi.model.Article;
import com.kazyon.orderapi.model.Order;
import com.kazyon.orderapi.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine,Long> {
    List<OrderLine> findAllByOrderByCreatedAtDesc();
    Optional<OrderLine> findByOrderAndArticle(Order order, Article article);
}
