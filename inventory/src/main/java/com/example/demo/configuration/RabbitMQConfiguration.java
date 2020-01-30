package com.example.demo.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
	
	 @Value("${spring.rabbitmq.hostname}")
	    private String hostname;

	    @Value("${spring.rabbitmq.username}")
	    private String username;

	    @Value("${spring.rabbitmq.password}")
	    private String password;

	    @Value("${spring.application.exchange.name}")
	    private String exchangeName;

	    @Value("${spring.application.queue.name}")
	    private String queueName;
	    
	    @Value("${spring.application.routing.key}")
	    private String routingKey;
	    
	    @Bean
	    Queue getQueue() {
	        return new Queue(queueName, true);
	    }
	    
	    @Bean
	    TopicExchange eventBusExchange() {
	        return new TopicExchange(exchangeName);
	    }
	    
	    @Bean
	    public Binding declareBinding() {
	    return BindingBuilder.bind(getQueue()).to(eventBusExchange()).with(routingKey);
	    }
	    
	    @Bean
	    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
	    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
	    return rabbitTemplate;
	    }

}
