package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Product;

public interface ProductService {
	public List<Product> getAllProducts(); 
	public void addProduct(Product product);
	public String getProductIdByName(String name);
}
