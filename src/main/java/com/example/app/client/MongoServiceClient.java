package com.example.app.client;

import com.example.app.model.Inventory;
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

    @Override
    public Inventory getInventory() {
        return restTemplate.getForObject(
                mongoServiceUrl,
                Inventory.class, getHeaders());
    }



}
