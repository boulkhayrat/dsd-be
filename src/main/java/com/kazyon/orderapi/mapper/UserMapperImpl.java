package com.kazyon.orderapi.mapper;

import com.kazyon.orderapi.dto.OrderDto;
import com.kazyon.orderapi.dto.OrderLineDto;
import com.kazyon.orderapi.model.Order;
import com.kazyon.orderapi.model.OrderLine;
import com.kazyon.orderapi.model.Supplier;
import com.kazyon.orderapi.model.User;
import com.kazyon.orderapi.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        List<UserDto.OrderDto> orders = user.getOrders().stream().map(this::toUserDtoOrderDto).toList();
        UserDto.StoreDto storeDto = null;
        if(user.getStore() != null){
            storeDto = new UserDto.StoreDto(user.getStore().getId(),user.getStore().getName(),user.getStore().getCode(),user.getStore().getAddress(),user.getStore().getCity(),user.getStore().getBusArea());
        }
        return new UserDto(user.getId(), user.getUsername(), user.getName(), user.getEmail(), user.getRole(),storeDto, orders);
    }

    private UserDto.OrderDto toUserDtoOrderDto(Order order) {
        if (order == null) {
            return null;
        }
        List<UserDto.OrderDto.OrderLineDto> orderLineDtos = null;
        if (order.getOrderLines() != null) {
            orderLineDtos = order.getOrderLines().stream()
                    .map(this::toOrderLineDto)
                    .collect(Collectors.toList());
        }

        return new UserDto.OrderDto(order.getId(), order.getNote(), order.getReference(),order.getDeliveryDate(),order.isValid(),orderLineDtos,toSupplierDto(order.getSupplier()),order.getCreatedAt());
    }

    private UserDto.OrderDto.SupplierDto toSupplierDto(Supplier supplier){
        if(supplier == null){
            return null;
        }
        return new UserDto.OrderDto.SupplierDto(supplier.getName());
    }
    private UserDto.OrderDto.OrderLineDto toOrderLineDto(OrderLine orderLine){
        if(orderLine == null){
            return null;
        }
        return new UserDto.OrderDto.OrderLineDto(
                orderLine.getId(),
                orderLine.getArticle().getName(),
                orderLine.getArticle().getId(),
                orderLine.getOrder().getId(),
                orderLine.getArticle().getCode(),
                orderLine.getOrder().getReference(),
                orderLine.getQuantity()
        );
    }
}
