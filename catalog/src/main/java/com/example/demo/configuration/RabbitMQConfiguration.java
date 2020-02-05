package com.example.demo.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import com.example.demo.service.CatalogServiceImpl;
import com.rabbitmq.client.Channel;

@Configuration
public class RabbitMQConfiguration {

	@Value("${spring.rabbitmq.hostname}")
	private String hostname;

	@Value("${spring.rabbitmq.username}")
	private String username;

	@Value("${spring.rabbitmq.password}")
	private String password;


	@Bean
	ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(hostname);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		return connectionFactory;
	}
	
	/*
	 * @Bean MessageListenerContainer messageListenerContainer(ConnectionFactory
	 * connectionFactory ) { SimpleMessageListenerContainer
	 * simpleMessageListenerContainer = new SimpleMessageListenerContainer();
	 * simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
	 * simpleMessageListenerContainer.setQueues(queue());
	 * simpleMessageListenerContainer.setMessageListener(new RabbitMQListner());
	 * return simpleMessageListenerContainer;
	 * 
	 * }
	 */


}
