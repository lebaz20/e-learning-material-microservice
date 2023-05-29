package com.hnj.inventory.service;

import com.hnj.inventory.model.Inventory;
import com.hnj.inventory.model.request.InventoryRequest;

import java.util.List;

public interface InventoryService {
    void updateInventory(InventoryRequest inventoryRequest);

    List<Inventory> getInventory();
}
