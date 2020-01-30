package com.example.demo.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping(value="/orders")
public class OrderController {
	
	private final static Logger LOG = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderRepository orderRepository;
	
	@HystrixCommand
	@RequestMapping(value="/testOrder/{productName}",method = RequestMethod.POST)
	public String testOrder(@PathVariable("productName") String productName){
		
		Order order = new Order();
		order.setQuantity(2);
		order.setProductName(productName);
		orderRepository.save(order);
		return "success";
		
	}
	@HystrixCommand
	@RequestMapping(value="/hystrixCircuitOpen",method= RequestMethod.GET)
	public String hystrixCircuitOpen() {
		
		String response = orderService.openCircuit();
		
		return response;
		
	}
	
	@HystrixCommand
	@RequestMapping(value="/placeOrder/{productName}",method=RequestMethod.POST)
	public String placeOrder(@PathVariable("productName") String productName,@RequestParam(name="quantity",required = true) int qty) throws RestClientException, IOException {
		
		LOG.debug("inside placeOrder :: Required prodName {} and quantity {}", productName, qty);
		
		String response = orderService.checkAvailability(productName,qty);
		
		
		LOG.debug("response :::::quatity {}", qty);
		//orderControllerClient.getProducts();
		return response;
	}

}
