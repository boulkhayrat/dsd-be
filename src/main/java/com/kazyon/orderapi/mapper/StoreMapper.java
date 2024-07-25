package com.kazyon.orderapi.mapper;


import com.kazyon.orderapi.dto.CreateStoreRequest;
import com.kazyon.orderapi.dto.StoreDto;
import com.kazyon.orderapi.model.Store;

public interface StoreMapper {

    Store toStore(CreateStoreRequest createStoreRequest);
    StoreDto toStoreDto(Store store);

}
