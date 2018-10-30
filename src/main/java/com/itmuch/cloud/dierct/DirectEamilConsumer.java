package com.itmuch.cloud.dierct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "direct_eamil_queue")
public class DirectEamilConsumer {
	
	@RabbitHandler
	public void process(String msg) throws Exception {
		System.out.println("邮件消费者获取生产者消息msg:" + msg);
	}
}