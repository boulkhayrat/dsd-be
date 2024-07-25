package com.kazyon.orderapi.service;

import com.kazyon.orderapi.model.User;
import com.kazyon.orderapi.repository.UserRepository;
import com.kazyon.orderapi.security.IpBasedAuthenticationService;
import com.kazyon.orderapi.security.WebSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IpBasedAuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private IpBasedAuthenticationService ipBasedAuthenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetStoreCodeFromIp() {
        String ip = "10.200.50.3";
        String expectedStoreCode = "M050";
        String storeCode = ipBasedAuthenticationService.getStoreCodeFromIp(ip);
        assertEquals(expectedStoreCode, storeCode);
    }

    @Test
    public void testAuthenticateStoreByIp() {
        String ip = "10.200.50.3";
        String storeCode = "M050";
        User user = new User();
        user.setUsername(storeCode);
        user.setRole(WebSecurityConfig.STORE);

        when(userRepository.findByUsername(storeCode)).thenReturn(Optional.of(user));

        boolean isAuthenticated = ipBasedAuthenticationService.authenticateStoreByIp(ip);
        assertTrue(isAuthenticated);

        when(userRepository.findByUsername(storeCode)).thenReturn(Optional.empty());

        isAuthenticated = ipBasedAuthenticationService.authenticateStoreByIp(ip);
        assertFalse(isAuthenticated);
    }
}
