package com.example.demo.service;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import com.example.demo.bean.MessageBean;
import com.fasterxml.jackson.databind.util.ObjectBuffer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;    


@Component
@RabbitListener(queues = "inventory-queue")
public class CatalogServiceImpl{
	
	private static final Logger LOG = LoggerFactory.getLogger(CatalogServiceImpl.class);

	/*
	 * @Override public void onMessage(Message message) { // TODO Auto-generated
	 * method stub
	 * 
	 * }
	 */
    @RabbitListener(queues = "inventory-queue")
	public void receiveMessage(byte[] messagePayload) {
    	
    	//String message = (String) messagePayload;
    	MessageBean message = null;
		try {
			message = new ObjectMapper().readValue(messagePayload,MessageBean.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	    System.out.println("Received <" + message + ">");
	    LOG.info("Received message: {} from app1 queue", message);
	    LOG.info("{} is Out of Stock", message.getProdId());
	    //message.
	    
	  }
}