package com.example.app.client;


import com.example.app.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RedisServiceClient implements ExternalServiceClient {

    @Value("${redis.service.url}")
    private String redisServiceUrl;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Inventory getInventory() {
        return restTemplate.getForObject(
                redisServiceUrl,
                Inventory.class, getHeaders());
    }

}
