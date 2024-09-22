package com.example.inventoryService.model;

import lombok.Getter;

@Getter
public enum Item {
    TV("Television", 1000) ,
    FRIDGE ("Refridgerator", 1001) ,
    WASHING_MACHINE ("Washing Machine", 1002);

    private final String displayName;
    private final Integer id;

    Item(String displayName, Integer id) {
        this.displayName = displayName;
        this.id = id;
    }

}
