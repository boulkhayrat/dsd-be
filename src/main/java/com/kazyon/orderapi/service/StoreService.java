package com.kazyon.orderapi.service;

import com.kazyon.orderapi.dto.UpdateStoreRequest;
import com.kazyon.orderapi.model.Store;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StoreService {

    List<Store> getStores();


    Store validateAndGetStore(Long id);

    Store updateStore(Long id, UpdateStoreRequest updateStoreRequest);
    Store saveStore(Store article);

    void deleteStore(Store article);
    List<Store> searchStores(String text);
}
