package com.cts.elearn.config;

import com.cts.elearn.event.CoursePurchasedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    /* =====================================================
       CONSUMER FACTORIES
       ===================================================== */

    // Typed Consumer (CoursePurchasedEvent)
    @Bean
    public ConsumerFactory<String, CoursePurchasedEvent> coursePurchasedConsumerFactory() {

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "purchasehistory-service");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        JsonDeserializer<CoursePurchasedEvent> deserializer =
                new JsonDeserializer<>(CoursePurchasedEvent.class);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    // Generic Consumer (for retry + DLT)
    @Bean
    public ConsumerFactory<String, Object> genericConsumerFactory() {

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "purchasehistory-service");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        JsonDeserializer<Object> deserializer = new JsonDeserializer<>();
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    /* =====================================================
       LISTENER CONTAINER FACTORIES
       ===================================================== */

    // Normal listener (typed)
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CoursePurchasedEvent>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, CoursePurchasedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(coursePurchasedConsumerFactory());
        return factory;
    }

    // Listener with retry + Dead Letter Topic
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object>
    kafkaListenerContainerFactoryUpgrade(
            ConsumerFactory<String, Object> genericConsumerFactory,
            KafkaTemplate<String, Object> kafkaTemplate) {

        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(genericConsumerFactory);

        DefaultErrorHandler errorHandler =
                new DefaultErrorHandler(
                        new DeadLetterPublishingRecoverer(kafkaTemplate),
                        new FixedBackOff(2000L, 3) // 3 retries, 2 sec delay
                );

        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }

    /* =====================================================
       PRODUCER (FOR DLT)
       ===================================================== */
}
