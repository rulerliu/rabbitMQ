package com.itmuch.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itmuch.cloud.entity.User;
import com.itmuch.cloud.fanout.FanoutSender;
import com.itmuch.cloud.hello.HelloSender;
import com.itmuch.cloud.many.Sender;
import com.itmuch.cloud.many.Sender2;
import com.itmuch.cloud.object.ObjectSender;
import com.itmuch.cloud.topic.TopicSender;

@RestController
public class RabbitController {
	
	@Autowired
	private HelloSender helloSender;
	
	@Autowired
	private Sender neoSender;

	@Autowired
	private Sender2 neoSender2;
	
	@Autowired
	private ObjectSender objectSender;

	@Autowired
	private FanoutSender fanoutSender;
	
	@Autowired
	private TopicSender topicSender;
	
	@RequestMapping("/hello")
	public String hello() {
		helloSender.send();
		return "success";
	}
	
	/**
	 * 一个发送者，2个消费者,经过测试会均匀的将消息发送到2个消费者中(2个消费者，总共接受10条消息，各5条)
	 * @throws Exception
	 */
	@RequestMapping("/oneToMany")
	public String one2Many() {
		for (int i = 0; i < 10; i++) {
			neoSender.send(i);
		}
		return "success";
	}
	
	/**
	 * n个提供者各发送10条消息，和一对多一样，接收端仍然会均匀接收到消息（均匀各接受10条）
	 * @throws Exception
	 */
	@RequestMapping("/manyToMany")
	public String many2Many() {
		for (int i = 0; i < 10; i++) {
			neoSender.send(i);
			neoSender2.send(i);
		}
		return "success";
	}
	
	@RequestMapping("/object")
	public String object() {
		User user=new User();
		user.setName("neo");
		user.setUsername("123456");
		objectSender.send(user);
		return "success";
	}
	
	/**
	 * 只要绑定了交换机的队列都能收到
	 * @return
	 */
	@RequestMapping("/fanout")
	public String fanout() {
		fanoutSender.send();
		return "success";
	}
	
	/**
	 *    #匹配0个字符或者一个以上，，，*匹配一个字符
	 * @return
	 */
	@RequestMapping("/topic")
	public String topic() {
		topicSender.send();
		return "success";
	}
	
	@RequestMapping("/topic1")
	public String topic1() {
		topicSender.send1();
		return "success";
	}
	
	@RequestMapping("/topic2")
	public String topic2() {
		topicSender.send2();
		return "success";
	}
	
}
