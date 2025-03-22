package com.cts.elearn.client;

import com.cts.elearn.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8085") // Change to Eureka if needed
public interface UserClient {

    @GetMapping("/users/{id}") 
    UserResponse getUserById(@PathVariable("id") int id);
}
