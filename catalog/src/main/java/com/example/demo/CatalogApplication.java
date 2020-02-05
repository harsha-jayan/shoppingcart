package com.example.demo;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.example.demo.service.CatalogServiceImpl;

@SpringBootApplication
@EnableEurekaClient
public class CatalogApplication {
	  
		public static void main(String[] args) {
			SpringApplication.run(CatalogApplication.class, args);
		}
}
