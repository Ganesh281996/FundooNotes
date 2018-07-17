package com.fundoonotes.configuration;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration 
{
	@Bean
	public Queue queue()
	{
		return new Queue("mailqueue",false);
	}
	
	@Bean
	public DirectExchange directExchange()
	{
		return new DirectExchange("mailexchange");
	}
	
	@Bean
	public Binding binding(Queue queue,DirectExchange exchange)
	{
		return BindingBuilder.bind(queue).to(exchange).with("mailkey");
	}
	
	@Bean
	public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory)
	{
		AmqpTemplate amqpTemplate = new RabbitTemplate(connectionFactory);
		return amqpTemplate;
	}
}