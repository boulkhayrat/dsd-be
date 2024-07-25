package com.kazyon.orderapi.controller;

import com.kazyon.orderapi.mapper.StoreMapper;
import com.kazyon.orderapi.model.Store;
import com.kazyon.orderapi.model.User;
import com.kazyon.orderapi.dto.CreateStoreRequest;
import com.kazyon.orderapi.dto.StoreDto;
import com.kazyon.orderapi.dto.UpdateStoreRequest;
import com.kazyon.orderapi.security.CustomUserDetails;
import com.kazyon.orderapi.service.StoreService;
import com.kazyon.orderapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final UserService userService;
    private final StoreService storeService;
    private final StoreMapper storeMapper;

    @GetMapping
    public List<StoreDto> getStores(@RequestParam(value = "text", required = false) String text) {
        List<Store> stores = (text == null) ? storeService.getStores() : storeService.searchStores(text);
        return stores.stream()
                .map(storeMapper::toStoreDto)
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public StoreDto createStore(@AuthenticationPrincipal CustomUserDetails currentUser,
                                    @Valid @RequestBody CreateStoreRequest createStoreRequest) {

        System.out.println("createStoreRequest" + createStoreRequest);
        User user = userService.validateAndGetUserByUsername(currentUser.getUsername());
        Store store = storeMapper.toStore(createStoreRequest);
        store.setUser(user);
        return storeMapper.toStoreDto(storeService.saveStore(store));
    }

    @DeleteMapping("/{id}")
    public StoreDto deleteStores(@PathVariable Long id) {
        Store store = storeService.validateAndGetStore(id);
        storeService.deleteStore(store);
        return storeMapper.toStoreDto(store);
    }

    @PutMapping("/{storeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public StoreDto updateStore(@AuthenticationPrincipal CustomUserDetails currentUser,
                                    @PathVariable Long storeId,
                                    @Valid @RequestBody UpdateStoreRequest updateStoreRequest) {
        System.out.println(updateStoreRequest);
        userService.validateAndGetUserByUsername(currentUser.getUsername());
        Store updatedStore = storeService.updateStore(storeId, updateStoreRequest);
        return storeMapper.toStoreDto(updatedStore);
    }

    @GetMapping("/search")
    public List<StoreDto> searchStores(@RequestParam(required = false) String text) {
        List<Store> stores = storeService.searchStores(text);
        return stores.stream()
                .map(storeMapper::toStoreDto)
                .collect(Collectors.toList());
    }
}
