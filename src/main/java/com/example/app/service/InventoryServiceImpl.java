package com.example.app.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import com.example.app.client.MongoServiceClient;
import com.example.app.client.PostgresServiceClient;
import com.example.app.client.RedisServiceClient;
import com.example.app.model.Inventory;
import com.example.app.model.Item;
import com.example.app.model.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService{

    private static final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);
    @Autowired
    RedisServiceClient redisServiceClient;

    @Autowired
    MongoServiceClient mongoServiceClient;

    @Autowired
    PostgresServiceClient postgresServiceClient;


    @Override
    public Inventory getInventory() {

        log.info("Update");
        Map<Item, Long> itemToQuantityMap = new HashMap<>();

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<List<Stock>> stocksFromRedis =
                    executor.submit(() -> redisServiceClient.getInventory().getStockList());
            Future<List<Stock>> stocksFromMongo =
                    executor.submit(() -> mongoServiceClient.getInventory().getStockList());
            Future<List<Stock>> stocksFromPostgres =
                    executor.submit(() -> postgresServiceClient.getInventory().getStockList());

            Stream.of(stocksFromRedis.get(), stocksFromMongo.get(), stocksFromPostgres.get())
                    .flatMap(Collection::stream).forEach(stock -> itemToQuantityMap.put(stock.item(),
                            stock.quantity() + itemToQuantityMap.getOrDefault(stock.item(), 0L)));

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new Inventory(itemToQuantityMap.entrySet().stream().map(entry-> new Stock(entry.getKey(),
                entry.getValue())).toList());

    }

}
