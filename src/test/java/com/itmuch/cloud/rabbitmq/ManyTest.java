package com.itmuch.cloud.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.itmuch.cloud.many.Sender;
import com.itmuch.cloud.many.Sender2;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManyTest {
	
	@Autowired
	private Sender neoSender;

	@Autowired
	private Sender2 neoSender2;

	/**
	 * 一个发送者，N个接受者,经过测试会均匀的将消息发送到N个接收者中(N个接受者，总共接受10条消息，各5条)
	 * @throws Exception
	 */
	@Test
	public void oneToMany() throws Exception {
		for (int i = 0; i < 10; i++) {
			neoSender.send(i);
		}
	}

	/**
	 * N各提供者各发送10条消息，和一对多一样，接收端仍然会均匀接收到消息（均匀各接受10条）
	 * @throws Exception
	 */
	@Test
	public void manyToMany() throws Exception {
		for (int i = 0; i < 10; i++) {
			neoSender.send(i);
			neoSender2.send(i);
		}
	}

}
