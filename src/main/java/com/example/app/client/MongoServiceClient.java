package com.example.app.client;

import java.util.Arrays;

import com.example.app.model.Inventory;
import com.example.app.model.Item;
import com.example.app.model.Stock;
import com.example.app.service.InventoryServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MongoServiceClient implements ExternalServiceClient{

    @Value("${mongo.service.url}")
    private String mongoServiceUrl;

    @Autowired
    RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(MongoServiceClient.class);

    @Override
    @CircuitBreaker(name = "mongoClientCircuitBreaker", fallbackMethod = "getInventoryFallBack")
    public Inventory getInventory() {
        log.info("Fetching inventory from mongo MS");
        return restTemplate.getForObject(
                mongoServiceUrl,
                Inventory.class, getHeaders());
    }

    public Inventory getInventoryFallBack(Exception e) {
        log.info("Utilising mongo MS Fallback");
        return new Inventory(Arrays.asList(
                new Stock(Item.TV, 0L),
                new Stock(Item.FRIDGE, 0L),
                new Stock(Item.WASHING_MACHINE, 0L)
        ));
    }

}
