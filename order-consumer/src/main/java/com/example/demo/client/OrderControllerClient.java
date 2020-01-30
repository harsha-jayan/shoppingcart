package com.example.demo.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderControllerClient {
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	RestTemplate restTemplate;
	
	public ResponseEntity<String> getProducts() throws RestClientException, IOException {

		//String baseUrl = "http://localhost:8090/emp/controller/getDetails";
		List<ServiceInstance> instances=discoveryClient.getInstances("product-producer");
		ServiceInstance serviceInstance=instances.get(0); // IPs, Port
		String baseUrl=serviceInstance.getUri().toString();//http://ip:port/
		baseUrl=baseUrl+"/products/getProducts";
		ResponseEntity<String> response=null;
		try{
		response=restTemplate.exchange(baseUrl,
				HttpMethod.GET, getHeaders(),String.class);
		}catch (Exception ex)
		{
			System.out.println(ex);
		}
		System.out.println(response.getBody());
		return response;
	}
	
	public String getProductIdByName(String prodName) {
		
		
		  List<ServiceInstance> instances=discoveryClient.getInstances("product-producer"); ServiceInstance
		  serviceInstance=instances.get(0); // IPs, Port 
		  String baseUrl=serviceInstance.getUri().toString();//http://ip:port/
		  baseUrl=baseUrl+"/products/getProductIdByName/{prodName}";
		 
		//String baseUrl = "http://PRODUCT-PRODUCER/products/getProductIdByName/{prodName}";
		
		String response = restTemplate.exchange(baseUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, prodName).getBody();
		System.out.println("OrderControllerClient ::: prodId :::"+response);

		return response;
	}
	
	public int getQuantityByProdId(String prodId) throws RestClientException, IOException {


		List<ServiceInstance> instances = discoveryClient.getInstances("inventory"); 
		ServiceInstance serviceInstance = instances.get(0); // IPs, Port 
		String baseUrl = serviceInstance.getUri().toString();//http://ip:port/
		
		baseUrl = baseUrl+"/inventory/getQuantityByProductId/{prodId}";

		String response = restTemplate.exchange(baseUrl,
				HttpMethod.GET, getHeaders(), String.class, prodId).getBody();
		
	
		
		System.out.println("OrderControllerClient ::: getQuantityByProdId :::quantity :::"+response);

		return Integer.parseInt(response);	
	}
	public String updateInventory(String prodId, int updateQuantity) throws RestClientException, IOException {

		System.out.println("inside OrderClient ::::updateInventory");

		List<ServiceInstance> instances = discoveryClient.getInstances("inventory"); 
		ServiceInstance serviceInstance = instances.get(0); // IPs, Port 
		String baseUrl = serviceInstance.getUri().toString();//http://ip:port/
		
		baseUrl = baseUrl + "/inventory/updateInventory/" + prodId + "/" + updateQuantity;
		//baseUrl = baseUrl+"/inventory/updateInventory{prodId}/{quantity}";
		
		System.out.println("baseUrl ::: "+baseUrl);

		String response = restTemplate.exchange(baseUrl,
				HttpMethod.PUT, getHeaders(), String.class).getBody();
		
	
		
		System.out.println("OrderControllerClient ::: updateInventory :::quantity :::"+response);

		return response;	
	}
	
	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity(headers);
	}


}
