package com.cts.elearn;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(properties = {
    "twilio.account.sid=dummySid",
    "twilio.auth.token=dummyToken",
    "twilio.phone.number=dummyNumber"
})
@ContextConfiguration(classes = NotificationServiceApplication.class)
class NotificationServiceApplicationTests {
    @Test
    void contextLoads() {
    }
}

