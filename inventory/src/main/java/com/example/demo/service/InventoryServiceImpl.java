package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.InventoryEntity;
import com.example.demo.repository.InventoryRepository;

@Service
public class InventoryServiceImpl implements InventoryService {
	
	@Autowired
	InventoryRepository inventoryRepository;

	@Override
	public int getQuantityByProductId(String prodId) {
		
		InventoryEntity inventoryEntity = inventoryRepository.findByProductId(prodId);
		return inventoryEntity.getQuantity();
	}

	@Override
	public String updateInventory(String prodId, int qty) {
		// TODO Auto-generated method stub
		InventoryEntity inventoryEntity = inventoryRepository.findByProductId(prodId);
		inventoryEntity.setQuantity(qty);
		inventoryRepository.save(inventoryEntity);
		return "updated Inventory successfully";

	}


}
