package com.itmuch.cloud.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.itmuch.cloud.topic.TopicSender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicTest {

	@Autowired
	private TopicSender sender;

	@Test
	public void topic() throws Exception {
		// 只有路由键带#号的TopicReceiver2能消费
//		sender.send("topic");
		
		// 路由键带*和#号的TopicReceiver2，TopicReceiver3能消费
//		sender.send("topic.test");
		
		// 三个队列都可以消费
//		sender.send("topic.message");
		
		// 只有路由键带#号的TopicReceiver2能消费
		sender.send("topic.message.test");
	}

	
}
