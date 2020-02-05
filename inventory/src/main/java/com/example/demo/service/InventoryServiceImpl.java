package com.example.demo.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.bean.MessageBean;
import com.example.demo.configuration.RabbitMQConfiguration;
import com.example.demo.entity.InventoryEntity;
import com.example.demo.repository.InventoryRepository;

@Service
public class InventoryServiceImpl implements InventoryService {
	
	private static final Logger LOG = LoggerFactory.getLogger(InventoryServiceImpl.class);

	
	@Autowired
	InventoryRepository inventoryRepository;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${spring.application.routing.key}") 
	private String routingKey;
	
	@Value("${spring.application.exchange.name}") 
	private String exchange;

	@Override
	public int getQuantityByProductId(String prodId) {
		
		InventoryEntity inventoryEntity = inventoryRepository.findByProductId(prodId);
		return inventoryEntity.getQuantity();
	}

	@Override
	@Transactional
	public String updateInventory(String prodId, int qty) {
		InventoryEntity inventoryEntity = inventoryRepository.findByProductId(prodId);
		inventoryEntity.setQuantity(qty);
		inventoryRepository.save(inventoryEntity);
		if (0 == qty) {
		System.out.println("Sending message to RabbitMQ:::");
		MessageBean messageBean = new MessageBean();
		messageBean.setProdId(prodId);
		messageBean.setQty(qty);
		rabbitTemplate.convertAndSend(exchange,routingKey, messageBean);
		LOG.info("message has been sent to the queue");
		}
		return "updated Inventory successfully";

	}


}
