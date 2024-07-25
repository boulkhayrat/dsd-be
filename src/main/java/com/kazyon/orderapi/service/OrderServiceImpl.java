package com.kazyon.orderapi.service;

import com.kazyon.orderapi.exception.ResourceNotFoundException;
import com.kazyon.orderapi.model.Supplier;
import com.kazyon.orderapi.repository.OrderRepository;
import com.kazyon.orderapi.model.Order;
import com.kazyon.orderapi.dto.UpdateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final SupplierService supplierService;

    private static final String REFERENCE_PREFIX = "ORD";
    private static final int REFERENCE_LENGTH = 5; // Length of the numerical part of the reference
    private static final AtomicLong orderCounter = new AtomicLong(0); // Counter for order references

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public List<Order> getOrdersContainingText(String text) {
        return orderRepository.findByNoteContainingIgnoreCaseOrderByCreatedAt(text);
    }

    @Override
    public Order validateAndGetOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order with id %s not found", id)));
    }

    @Override
    public Order saveOrder(Order order) {
        // Generate order reference
        String reference = generateOrderReference();
        order.setReference(reference);

        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long orderId, UpdateOrderRequest updateOrderRequest) {
        System.out.println("updated Request in service: "+updateOrderRequest);
        Order existingOrder = validateAndGetOrder(orderId);

        // Update order properties
        existingOrder.setNote(updateOrderRequest.getNote());
        existingOrder.setReference(updateOrderRequest.getReference());
        existingOrder.setDeliveryDate(updateOrderRequest.getDeliveryDate());
        existingOrder.setValid(updateOrderRequest.isValid());
        existingOrder.setValidationDate(updateOrderRequest.getValidationDate());
        Supplier supplier = supplierService.validateAndGetSupplier(updateOrderRequest.getSupplier_id());
        existingOrder.setSupplier(supplier);



        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public List<Order> searchOrders(String description, String reference) {
        return orderRepository.findByNoteContainingIgnoreCaseOrReferenceContainingIgnoreCaseOrderByCreatedAt(description, reference);
    }

    private String generateOrderReference() {
        long nextSequence = orderCounter.incrementAndGet();
        return String.format("%s%0" + REFERENCE_LENGTH + "d", REFERENCE_PREFIX, nextSequence);
    }
}
