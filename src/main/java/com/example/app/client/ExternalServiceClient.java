package com.example.app.client;

import java.util.HashMap;

import com.example.app.model.Inventory;

public interface ExternalServiceClient {
    Inventory getInventory();

    default HashMap<String, String> getHeaders() {
        return new HashMap<>();
    }

}
