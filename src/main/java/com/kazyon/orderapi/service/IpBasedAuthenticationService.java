package com.kazyon.orderapi.service;

import com.kazyon.orderapi.model.User;
import com.kazyon.orderapi.repository.UserRepository;
import com.kazyon.orderapi.security.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IpBasedAuthenticationService {

    @Autowired
    private UserRepository userRepository;
    public String getStoreCodeFromIp(String ip) {
        // Handle the IPv6 loopback address
        if ("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip)) {
            return "user";  
//            ip = "10.200.1.3";
        }
        String[] ipParts = ip.split("\\.");
        if (ipParts.length != 4) {
            throw new IllegalArgumentException("Invalid IP address");
        }
        return "M" + String.format("%03d", ((Integer.parseInt(ipParts[1]) - 200) * 250) + Integer.parseInt(ipParts[2]));
    }

    public boolean authenticateStoreByIp(String ip) {
        System.out.println(ip);
        String storeCode = getStoreCodeFromIp(ip);
        System.out.println(storeCode);
        Optional<User> userOptional = userRepository.findByUsername(storeCode);
        System.out.println(userOptional);
        if(userOptional.isPresent()) {
            System.out.println(userOptional);
            User user = userOptional.get();
            return  WebSecurityConfig.STORE.equals(user.getRole());
        }

        return false;
    }
}
