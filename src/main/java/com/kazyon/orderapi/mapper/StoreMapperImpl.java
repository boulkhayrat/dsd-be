package com.kazyon.orderapi.mapper;


import com.kazyon.orderapi.dto.CreateStoreRequest;
import com.kazyon.orderapi.dto.StoreDto;
import com.kazyon.orderapi.model.Store;
import org.springframework.stereotype.Repository;

@Repository
public class StoreMapperImpl implements StoreMapper{

    @Override
    public Store toStore(CreateStoreRequest createStoreRequest) {
        if(createStoreRequest == null){
            return null;
        }
        Store store  = new Store();
        store.setBusArea(createStoreRequest.getBusArea());
        store.setName(createStoreRequest.getName());
        store.setCode(createStoreRequest.getCode());
        store.setCity(createStoreRequest.getCity());
        store.setAddress(createStoreRequest.getAddress());

        return store;
    }

    @Override
    public StoreDto toStoreDto(Store store) {
        if(store == null){
            return null;
        }else {
            StoreDto.UserDto userDto = null;
            if(store.getUser() != null){
                userDto = new StoreDto.UserDto(store.getUser().getUsername());
            }
            return new StoreDto(store.getId(),store.getCode(),store.getCity(),store.getName(),store.getAddress(),userDto,store.getCreatedAt());
        }


    }
}
