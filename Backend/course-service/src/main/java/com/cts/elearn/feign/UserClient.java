package com.cts.elearn.feign;

import com.cts.elearn.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8085") // URL should point to your User Service
public interface UserClient {
    @GetMapping("/users/{userId}")
    UserResponse getUserById(@PathVariable("userId") int userId);
}
