package com.example.app.model;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Inventory {
    private List<Stock> stockList;

    public List<Stock> getStockList() {
        return stockList;
    }

    public Inventory(List<Stock> stocks) {
        this.stockList = stocks;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    public Inventory() {

    }

}
