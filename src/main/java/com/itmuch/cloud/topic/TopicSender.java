package com.itmuch.cloud.topic;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send(String routingKey) {
		String context = "hi, i am topic message";
		System.out.println("Sender : " + context);
		this.rabbitTemplate.convertAndSend("topicExchange", routingKey, context);
	}

}