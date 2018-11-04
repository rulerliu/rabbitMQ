package com.itmuch.cloud.dierct;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DirectSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send(String routingKey) {
		String msg = "hi, direct msg ";
		System.out.println("Direct Sender : " + msg);
		this.rabbitTemplate.convertAndSend("directExchange", routingKey, msg);
	}
}
