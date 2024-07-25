package com.kazyon.orderapi.mapper;

import com.kazyon.orderapi.dto.CreateOrderRequest;
import com.kazyon.orderapi.dto.OrderDto;
import com.kazyon.orderapi.dto.UpdateOrderRequest;
import com.kazyon.orderapi.model.Order;
import com.kazyon.orderapi.model.OrderLine;
import com.kazyon.orderapi.model.Supplier;
import com.kazyon.orderapi.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private  SupplierService supplierService;

    @Override
    public Order toOrder(CreateOrderRequest createOrderRequest) {
        if (createOrderRequest == null) {
            return null;
        }



        Order order = new Order();
        order.setNote(createOrderRequest.getNote());
        order.setReference(createOrderRequest.getReference());
        order.setDeliveryDate(createOrderRequest.getDeliveryDate());
        order.setValid(createOrderRequest.isValid());
        Long supplier_id = createOrderRequest.getSupplier_id();

        if(supplier_id != null) {
            Supplier supplier = supplierService.validateAndGetSupplier(supplier_id);
            order.setSupplier(supplier);
        }


        return order;
    }

    @Override
    public OrderDto toOrderDto(Order order) {
        if (order == null) {
            return null;
        }

        OrderDto.UserDto userDto = null;
        if (order.getUser() != null) {
            userDto = new OrderDto.UserDto(order.getUser().getUsername());
        }

        OrderDto.SupplierDto supplierDto = null;
        if (order.getSupplier() != null) {
            supplierDto = new OrderDto.SupplierDto(order.getSupplier().getId(),order.getSupplier().getCode(),order.getSupplier().getName());
        }

        OrderDto.StoreDto storeDto = null;
        if (order.getStore() != null) {
            storeDto = new OrderDto.StoreDto(order.getStore().getName());
        }

        List<OrderDto.OrderLineDto> orderLineDtos = null;
        if (order.getOrderLines() != null) {
            orderLineDtos = order.getOrderLines().stream()
                    .map(this::toOrderLineDto)
                    .collect(Collectors.toList());
        }

        return new OrderDto(
                order.getId(),
                order.getReference(),
                order.getNote(),
                order.getDeliveryDate(),
                order.isValid(),
                order.getValidationDate(),
                supplierDto,
                storeDto,
                userDto,
                orderLineDtos,
                order.getCreatedAt()

        );
    }

    private OrderDto.OrderLineDto toOrderLineDto(OrderLine orderLine) {
        if (orderLine == null) {
            return null;
        }

        return new OrderDto.OrderLineDto(
                orderLine.getId(),
                orderLine.getArticle().getName(),
                orderLine.getArticle().getId(),
                orderLine.getArticle().getCode(),
                orderLine.getOrder().getId(),
                orderLine.getOrder().getReference(),
                orderLine.getQuantity()
        );
    }
}
