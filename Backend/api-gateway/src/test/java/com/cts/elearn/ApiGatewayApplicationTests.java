package com.cts.elearn;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = ApiGatewayApplication.class)
@ActiveProfiles("test")
//@Import(TestSecurityConfig.class) // Ensure test security config is used
class ApiGatewayApplicationTests {

    @Test
    void contextLoads() {
        // Test only checks if Spring context loads
    }
}
