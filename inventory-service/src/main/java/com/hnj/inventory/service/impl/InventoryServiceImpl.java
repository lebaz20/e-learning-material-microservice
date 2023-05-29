package com.hnj.inventory.service.impl;

import com.hnj.inventory.event.EventDispatcher;
import com.hnj.inventory.event.ProductInventoryEvent;
import com.hnj.inventory.model.Inventory;
import com.hnj.inventory.model.request.InventoryRequest;
import com.hnj.inventory.repository.InventoryRepository;
import com.hnj.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

	private final InventoryRepository inventoryRepository;
	private EventDispatcher eventDispatcher;

	@Autowired
	public InventoryServiceImpl(InventoryRepository inventoryRepository,
							EventDispatcher eventDispatcher) {
		this.inventoryRepository = inventoryRepository;
		this.eventDispatcher = eventDispatcher;
	}

	@Transactional
	@Override
	public void updateInventory(InventoryRequest inventoryRequest) {
		Optional<Inventory> inventory = inventoryRepository.findByProductId(inventoryRequest.getProductId());
		if(inventory.isPresent()){
			inventory.get().setQuantity(inventoryRequest.getQuantity());
		}else {
			inventory = Optional.ofNullable(new Inventory().builder()
					.productId(inventoryRequest.getProductId())
					.quantity(inventoryRequest.getQuantity())
					.build());
		}
		inventoryRepository.save(inventory.get());
		eventDispatcher.send(new ProductInventoryEvent(inventory.get().getProductId(), inventory.get().getQuantity()));
	}

	@Override
	public List<Inventory> getInventory() {
		return inventoryRepository.findAll();
	}
}
