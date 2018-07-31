//package com.fundoonotes.configuration;
//
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//
//@Configuration
//@PropertySource(value = "classpath:rabbitmq.properties")
//public class RabbitMQConfiguration 
//{
//	@Autowired
//	Environment environment; 
//	
//	@Bean
//	public Queue queue()
//	{
//		return new Queue(environment.getProperty("mail.queue"),false);
//	}
//	
//	@Bean
//	public DirectExchange directExchange()
//	{
//		return new DirectExchange(environment.getProperty("mail.exchange"));
//	}
//	
//	@Bean
//	public Binding binding(Queue queue,DirectExchange exchange)
//	{
//		return BindingBuilder.bind(queue).to(exchange).with(environment.getProperty("mail.routingkey"));
//	}
//	
//	@Bean
//	public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory)
//	{
//		AmqpTemplate amqpTemplate = new RabbitTemplate(connectionFactory);
//		return amqpTemplate;
//	}
//}