package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;


@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired 
	ProductRepository productRepository;
	
	@Autowired
	ProductService productService;

	@RequestMapping(value="/addProduct",method=RequestMethod.POST) 
	public ResponseEntity<String> addProduct(@RequestBody Product product){
		
		productService.addProduct(product);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}


	@RequestMapping(value="/getProducts", method=RequestMethod.GET) 
	public List<Product> getProducts() {

		return productService.getAllProducts();
	}

	@RequestMapping(value="/getProductIdByName/{prodName}", method=RequestMethod.GET) 
	public String getProductIdByName(@PathVariable("prodName") String prodName) {
	
		String prodId = productService.getProductIdByName(prodName);
		

		return prodId;
	}


}
