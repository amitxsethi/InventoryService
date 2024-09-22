package com.example.app.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.example.app.client.MongoServiceClient;
import com.example.app.client.PostgresServiceClient;
import com.example.app.client.RedisServiceClient;
import com.example.app.model.Inventory;
import com.example.app.model.Item;
import com.example.app.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService{

    @Autowired
    RedisServiceClient redisServiceClient;

    @Autowired
    MongoServiceClient mongoServiceClient;

    @Autowired
    PostgresServiceClient postgresServiceClient;


    @Override
    public Inventory getInventory() {
        Map<Item, Long> itemToQuantityMap = new HashMap<>();

        List<Stock> stocksFromRedis =
                redisServiceClient.getInventory().getStockList();
        List<Stock> stocksFromMongo =
                mongoServiceClient.getInventory().getStockList();
        List<Stock> stocksFromPostgres =
                postgresServiceClient.getInventory().getStockList();

        Stream.of(stocksFromRedis, stocksFromMongo, stocksFromPostgres)
                .flatMap(Collection::stream).forEach(stock -> itemToQuantityMap.put(stock.item(),
                        stock.quantity() + itemToQuantityMap.getOrDefault(stock.item(), 0L)));

        return new Inventory(itemToQuantityMap.entrySet().stream().map(entry-> new Stock(entry.getKey(),
                entry.getValue())).toList());

    }

}
