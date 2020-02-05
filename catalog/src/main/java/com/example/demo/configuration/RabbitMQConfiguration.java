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

	@Value("${spring.application.exchange.name}")
	private String exchangeName;

	@Value("${spring.application.queue.name}")
	private String queueName;

	@Value("${spring.application.routing.key}")
	private String routingKey;

	@Bean
	Queue getQueue() {
		return new Queue(queueName);
	}

	@Bean
	TopicExchange getExchange() {
		return new TopicExchange(exchangeName);
	}

	@Bean
	public Binding declareBinding() {
		return BindingBuilder.bind(getQueue()).to(getExchange()).with(routingKey) ;
	}

	@Bean
	ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(hostname);

		/*
		 * Channel ch = connectionFactory.createConnection().createChannel(false);
		 * 
		 * Map<String, Object> args = new HashMap<String, Object>();
		 * args.put("alternate-exchange", "my-ae"); Channel ch
		 * =connectionFactory.createConnection().createChannel(false);
		 * ch.exchangeDeclare(exchangeName, "topic", false, false, args);
		 * ch.exchangeDeclare("my-ae", "fanout"); ch.queueDeclare(queueName, false,
		 * false, false, null); ch.queueBind(queueName, exchangeName, routingKey);
		 * 
		 * ch.queueDeclare("unrouted", false, false, false, null);
		 * ch.queueBind("unrouted", "my-ae", "");
		 * 
		 * 
		 * //ch.basicQos(1);
		 */
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		return connectionFactory;
	}

	/*
	 * @Bean
	 * 
	 * @Required RabbitAdmin rabbitAdmin() { RabbitAdmin admin = new
	 * RabbitAdmin(connectionFactory()); admin.setAutoStartup(true);
	 * admin.declareExchange(getExchange()); admin.declareQueue(getQueue());
	 * admin.declareBinding(declareBinding()); return admin; }
	 */
	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}

	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}

	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}

	/*
	 * @Bean MessageListenerAdapter listenerAdapter(CatalogServiceImpl receiver) {
	 * System.out.println("FRom Application:> Created Receiver Adapator: "+receiver)
	 * ; return new MessageListenerAdapter(receiver, "receiveMessage"); // it is
	 * telling which method is responsible for cosuming the message }
	 * 
	 * 
	 * @Bean SimpleMessageListenerContainer container(ConnectionFactory
	 * connectionFactory, MessageListenerAdapter messageAdapter) {
	 * System.out.println("FRom Application:> SimpleMessageListenerContainer: "
	 * +connectionFactory +"Adaptor is: ["+messageAdapter+"]");
	 * SimpleMessageListenerContainer container = new
	 * SimpleMessageListenerContainer();
	 * container.setConnectionFactory(connectionFactory);
	 * container.setQueueNames(queueName);
	 * container.setMessageListener(messageAdapter); return container; }
	 */

}
