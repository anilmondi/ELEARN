package com.cts.elearn.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.elearn.dto.UserResponse;

@FeignClient(name = "user-service", url = "http://localhost:8085")
public interface UserClient {
    @GetMapping("/users/{userId}")
    UserResponse getUserById(@PathVariable("userId") int userId);
}
