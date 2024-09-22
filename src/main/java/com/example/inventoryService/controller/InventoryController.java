package com.example.inventoryService.controller;

import java.util.Arrays;

import com.example.inventoryService.model.Inventory;
import com.example.inventoryService.model.Item;
import com.example.inventoryService.model.Stock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class InventoryController {

    @GetMapping("/inventory")
    public Inventory getInventory() {
        return new Inventory(Arrays.asList(
            new Stock(Item.TV, 0L),
            new Stock(Item.FRIDGE, 234L),
            new Stock(Item.WASHING_MACHINE, 1231L)
        ));
    }
}
