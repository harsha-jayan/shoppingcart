package com.example.demo.service;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.example.demo.client.OrderControllerClient;
import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;


@Service
public class OrderServiceImpl implements OrderService{
	
	private final static Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

	
	@Autowired
	OrderControllerClient orderControllerClient;
	
	@Autowired
	OrderRepository orderRepository;

	@Override
	public String getProductIdByName(String name) {
		
		String prodId = orderControllerClient.getProductIdByName(name);
				
		return prodId;
	}

	@Override
	public int getQuantityByProdId(String prodId) throws RestClientException, IOException {
		int quantity = orderControllerClient.getQuantityByProdId(prodId);
		return quantity;
	}

	@Override
	@HystrixCommand(fallbackMethod = "callFallback")
	public String checkAvailability(String productName, int orderedQuantity) {

		String result = null;

		try {
			String prodId = getProductIdByName(productName);

			int availabeQuantity = 0;
			String status = null;

			if(StringUtils.isNotBlank(prodId)) {
				availabeQuantity = getQuantityByProdId(prodId);

				if(availabeQuantity >= orderedQuantity) {

					Order order = new Order();
					order.setProductName(productName);
					order.setQuantity(orderedQuantity);
					orderRepository.save(order);

					int updateQuantity = availabeQuantity - orderedQuantity;
					status = updateInventory(prodId,updateQuantity);
				}
				result = "Your Order has been placed successfully";

			}else {

				result =  "Sorry The product is not available";
			} }catch (Exception ex) {
				System.out.println("Exception occured :::"+ex);
	            throw new RuntimeException("checkAvailability method has failed");
			}
		return result;
	}
	public String callFallback(String productName, int orderedQuantity/* , Throwable e */){
		//System.out.println("");
		return "Sorry!! Due to Internal Server error We could not process your order at this time";
	}
	
	
	public String updateInventory(String prodId, int updateQuantity) throws RestClientException, IOException {
		
		LOG.debug("inside InventoryController :::placeOrderAndUpdateInventory {}", updateQuantity);	
		System.out.println("updateQuantity "+updateQuantity);
		
		orderControllerClient.updateInventory(prodId,updateQuantity);
		
		return "inventory updated successfully";
		
	}

	@SuppressWarnings("unused")
	@Override
	@HystrixCommand(fallbackMethod = "callFallback2",commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", 
			value = "5000"),
			 @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") 
			}        										
		,
			threadPoolProperties={
						//sets thread pool size 
						@HystrixProperty(name="coreSize",value="10"),
						})
	public String openCircuit() {
		if (1>0) {
            throw new RuntimeException("checkAvailability method has failed");
		}
		return "Open circuit";
	}
	
	public String callFallback2(){
		//System.out.println("");
		return "Sorry!! Due to Internal Server error We could not process your order at this time";
	}
}
