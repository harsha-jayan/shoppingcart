package com.example.demo.service;

import org.springframework.stereotype.Service;

public interface InventoryService {
	//public String insertToInventory();
	public int getQuantityByProductId(String prodId);

	public String updateInventory(String prodId, int qty);
	
	public void sentToRabbitMq();
}
