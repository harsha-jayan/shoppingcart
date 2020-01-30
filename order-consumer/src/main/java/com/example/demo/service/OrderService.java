package com.example.demo.service;

import java.io.IOException;

import org.springframework.web.client.RestClientException;

public interface OrderService {
	
	public String getProductIdByName(String name);
	
	public String openCircuit();
	
	public int getQuantityByProdId(String prodId) throws RestClientException, IOException;
	
	public String checkAvailability(String productName, int orderedQuantity) throws RestClientException, IOException;

}
