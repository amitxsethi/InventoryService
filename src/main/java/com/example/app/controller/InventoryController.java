package com.example.app.controller;

import java.util.Arrays;

import com.example.app.model.Inventory;
import com.example.app.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @GetMapping("/inventory")
    public Inventory getInventory() {
        return inventoryService.getInventory();
    }
}
