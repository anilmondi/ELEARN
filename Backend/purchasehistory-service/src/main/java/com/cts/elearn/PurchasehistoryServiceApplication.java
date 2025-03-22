package com.cts.elearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = "com.cts.elearn.dao") // Ensures only JPA repositories are used
public class PurchasehistoryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PurchasehistoryServiceApplication.class, args);
    }
}
