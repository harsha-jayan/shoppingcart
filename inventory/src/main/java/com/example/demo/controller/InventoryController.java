package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.InventoryService;

@RestController
@RequestMapping(value="/inventory")
public class InventoryController {
	
	@Autowired
	InventoryService inventoryService;
	
	@RequestMapping(value="/getQuantityByProductId/{prodId}", method=RequestMethod.GET) 
	public int getQuantityByProductId(@PathVariable("prodId") String prodId) {
	
		int qty = inventoryService.getQuantityByProductId(prodId);
		return qty;
	}
	
	@RequestMapping(value="/updateInventory/{prodId}/{quantity}", method=RequestMethod.PUT) 
	public String updateInventory(@PathVariable("prodId") String prodId, @PathVariable("quantity") int quantity) {
	
		String result = inventoryService.updateInventory(prodId,quantity);
		return result;
	}

	@RequestMapping(value="/sentToRabbitMq",method=RequestMethod.GET)
	public String sentToRabbitMq() {
		inventoryService.sentToRabbitMq();
		return "messagesent";
	}

}
