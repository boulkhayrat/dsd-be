package com.kazyon.orderapi.service;

import com.kazyon.orderapi.dto.UpdateStoreRequest;
import com.kazyon.orderapi.exception.ResourceNotFoundException;
import com.kazyon.orderapi.model.Store;
import com.kazyon.orderapi.model.Supplier;
import com.kazyon.orderapi.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService{

    private final StoreRepository storeRepository;
    private final SupplierServiceImpl supplierService;

    @Override
    public List<Store> getStores() {
        return storeRepository.findAllByOrderByCreatedAtDesc();
    }



    @Override
    public Store validateAndGetStore(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Store with id %s not found", id)));
    }

    @Override
    public Store updateStore(Long id, UpdateStoreRequest updateStoreRequest) {
        Store existingStore = validateAndGetStore(id);
        existingStore.setName(updateStoreRequest.getName());
        existingStore.setCode(updateStoreRequest.getCode());
        existingStore.setCity(updateStoreRequest.getCity());
        existingStore.setAddress(updateStoreRequest.getAddress());
        existingStore.setBusArea(updateStoreRequest.getBusArea());

        return storeRepository.save(existingStore);
    }



    @Override
    public Store saveStore(Store store) {

        return storeRepository.save(store);
    }

    @Override
    public void deleteStore(Store store) {
        storeRepository.delete(store);

    }

    @Override
    public List<Store> searchStores(String text) {

            return storeRepository.findByAddressContainingIgnoreCaseOrNameContainingIgnoreCaseOrCodeContainingIgnoreCaseOrderByCreatedAt(text, text,text);

    }


}
