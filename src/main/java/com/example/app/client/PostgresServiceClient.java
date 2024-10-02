package com.example.app.client;


import java.util.Arrays;

import com.example.app.model.Inventory;
import com.example.app.model.Item;
import com.example.app.model.Stock;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PostgresServiceClient implements ExternalServiceClient {

    @Value("${postgres.service.url}")
    private String postgresServiceUrl;

    @Autowired
    RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(PostgresServiceClient.class);

    @Override
    @CircuitBreaker(name = "postgresClientCircuitBreaker", fallbackMethod = "getInventoryFallBack")
    public Inventory getInventory() {
        log.info("Fetching inventory from postgres MS");
        return restTemplate.getForObject(postgresServiceUrl, Inventory.class, getHeaders());
    }

    public Inventory getInventoryFallBack(Exception e) {
        log.info("Utilising postgres MS Fallback");
        return new Inventory(Arrays.asList(
                new Stock(Item.TV, 0L),
                new Stock(Item.FRIDGE, 0L),
                new Stock(Item.WASHING_MACHINE, 0L)
        ));
    }
}
