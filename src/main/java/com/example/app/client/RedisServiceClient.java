package com.example.app.client;


import java.util.Arrays;

import com.example.app.model.Inventory;
import com.example.app.model.Item;
import com.example.app.model.Stock;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RedisServiceClient implements ExternalServiceClient {

    private static final Logger log = LoggerFactory.getLogger(PostgresServiceClient.class);

    @Value("${redis.service.url}")
    private String redisServiceUrl;

    @Autowired
    RestTemplate restTemplate;

    @Override
    @CircuitBreaker(name = "redisClientCircuitBreaker", fallbackMethod = "getInventoryFallBack")
    public Inventory getInventory() {
      log.info("Fetching inventory from redis MS");
        return restTemplate.getForObject(
                redisServiceUrl,
                Inventory.class, getHeaders());
    }

    public Inventory getInventoryFallBack(Exception e) {
        log.info("Utilising redis MS Fallback");
        return new Inventory(Arrays.asList(
                new Stock(Item.TV, 0L),
                new Stock(Item.FRIDGE, 0L),
                new Stock(Item.WASHING_MACHINE, 0L)
        ));
    }

}
