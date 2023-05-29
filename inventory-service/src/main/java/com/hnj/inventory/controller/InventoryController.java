package com.hnj.inventory.controller;

import com.hnj.inventory.model.Inventory;
import com.hnj.inventory.model.request.InventoryRequest;
import com.hnj.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:3000", allowedHeaders="*")
public class InventoryController {
	private InventoryService inventoryService;

	@Autowired
	public InventoryController(InventoryService inventoryService){
		this.inventoryService = inventoryService;
	}

	@PostMapping("/inventory")
	public void updateInventory(@Valid @RequestBody InventoryRequest inventoryRequest){
		inventoryService.updateInventory(inventoryRequest);
	}

	@GetMapping("/inventory")
	public List<Inventory> getInventory(){
		return inventoryService.getInventory();
	}
}
