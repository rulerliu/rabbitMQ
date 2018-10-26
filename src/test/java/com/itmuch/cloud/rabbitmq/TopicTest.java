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
		sender.send();
	}

	/**
	 * 发送send1发送topic.message会匹配到topic.#和topic.message 两个Receiver都可以收到消息
	 * @throws Exception
	 */
	@Test
	public void topic1() throws Exception {
		sender.send1();
	}

	/**
	 * 发送send2发送topic.messages只有topic.#可以匹配所有只有Receiver2监听到消息
	 * @throws Exception
	 */
	@Test
	public void topic2() throws Exception {
		sender.send2();
	}
	
}
