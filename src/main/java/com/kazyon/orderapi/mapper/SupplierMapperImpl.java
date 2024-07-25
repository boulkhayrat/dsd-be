package com.kazyon.orderapi.mapper;

import com.kazyon.orderapi.dto.CreateSupplierRequest;
import com.kazyon.orderapi.dto.StoreDto;
import com.kazyon.orderapi.dto.SupplierDto;
import com.kazyon.orderapi.model.Article;
import com.kazyon.orderapi.model.Order;
import com.kazyon.orderapi.model.Store;
import com.kazyon.orderapi.model.Supplier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SupplierMapperImpl implements SupplierMapper {

    @Override
    public Supplier toSupplier(CreateSupplierRequest createSupplierRequest) {
        if (createSupplierRequest == null) {
            return null;
        }

        Supplier supplier = new Supplier();
        supplier.setName(createSupplierRequest.getName());
        supplier.setCode(createSupplierRequest.getCode());
        supplier.setAddress(createSupplierRequest.getAddress());
        supplier.setFiscalIds(createSupplierRequest.getFiscalIds());

        return supplier;
    }

    @Override
    public SupplierDto toSupplierDto(Supplier supplier) {
        if (supplier == null) {
            return null;
        }

        SupplierDto.UserDto userDto = null;
        if (supplier.getUser() != null) {
            userDto = new SupplierDto.UserDto(supplier.getUser().getUsername());
        }

        List<SupplierDto.OrderDto> ordersDto = null;
        if (supplier.getOrders() != null) {
            ordersDto = supplier.getOrders().stream()
                    .map(this::toOrderDto)
                    .collect(Collectors.toList());
        }

        List<SupplierDto.ArticleDto> articlesDto = null;
        if (supplier.getArticles() != null) {
            articlesDto = supplier.getArticles().stream()
                    .map(this::toArticleDto)
                    .collect(Collectors.toList());
        }

        return new SupplierDto(
                supplier.getId(),
                supplier.getCode(),
                supplier.getName(),
                supplier.getAddress(),
                supplier.getFiscalIds(),
                ordersDto,
                articlesDto,
                userDto,
                supplier.getCreatedAt()
        );
    }

    private SupplierDto.OrderDto toOrderDto(Order order) {
        if (order == null) {
            return null;
        }

        return new SupplierDto.OrderDto(
                order.getReference(),
                order.getNote(),
                order.getDeliveryDate(),
                order.isValid(),
                toStoreDto(order.getStore())
        );
    }
    private SupplierDto.ArticleDto toArticleDto(Article article) {
        if (article == null) {
            return null;
        }

        return new SupplierDto.ArticleDto(
                article.getId(),
                article.getCode(),

                article.getName(),
                article.getUnitOfMeasure()

        );
    }

    private StoreDto toStoreDto(Store store) {
        if (store == null) {
            return null;
        } else {
            StoreDto.UserDto userDto = null;
            if (store.getUser() != null) {
                userDto = new StoreDto.UserDto(store.getUser().getUsername());
            }
            return new StoreDto(
                    store.getId(),
                    store.getCode(),
                    store.getCity(),
                    store.getName(),
                    store.getAddress(),
                    userDto,
                    store.getCreatedAt());
        }
    }
}
