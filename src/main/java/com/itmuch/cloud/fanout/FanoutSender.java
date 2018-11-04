package com.itmuch.cloud.fanout;

import java.util.UUID;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
public class FanoutSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	/**
	 * 这里使用了email，sms 队列绑定到Fanout交换机上面，发送端的routing_key写任何字符都会被忽略：
	 */
	public void send() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", "644064779");
		jsonObject.put("timestamp", System.currentTimeMillis());
		String jsonString = jsonObject.toJSONString();
		System.out.println("Sender : " + jsonString);
		this.rabbitTemplate.convertAndSend("fanoutExchange", "", jsonString);
	}
	
	
	/**
	 * 消费者如何保证消息幂等性问题，不被重复消费？
	 * 产生原因：网络延迟中，消费出现异常或者是消费者延迟消费，会造成MQ进行重试补偿，在等待过程中，可能会造成重复消费
	 * 解决办法：使用全局MessageID判断消费方使用同一个，解决幂等性
	 */
	public void send2() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", "644064779");
		jsonObject.put("timestamp", System.currentTimeMillis());
		String jsonString = jsonObject.toJSONString();
		System.out.println("Sender : jsonString:" + jsonString);
		
		// 生产者发送消息的时候需要设置消息id
		Message message = MessageBuilder.withBody(jsonString.getBytes())
				.setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("utf-8")
				.setMessageId(UUID.randomUUID() + "").build();
		this.rabbitTemplate.convertAndSend("fanoutExchange2", "", message);
	}
	
	
	public void sendDead(int num) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("email", "644064779");
		jsonObject.put("num", num);
		String jsonString = jsonObject.toJSONString();
		System.out.println("Sender : jsonString:" + jsonString);
		
		// 生产者发送消息的时候需要设置消息id
		Message message = MessageBuilder.withBody(jsonString.getBytes())
				.setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("utf-8")
				.setMessageId(UUID.randomUUID() + "").build();
		this.rabbitTemplate.convertAndSend("fanoutDeadExchange", "", message);
	}

}