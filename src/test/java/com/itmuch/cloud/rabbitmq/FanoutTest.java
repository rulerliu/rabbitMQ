package com.itmuch.cloud.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.itmuch.cloud.fanout.FanoutSender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FanoutTest {

	@Autowired
	private FanoutSender sender;

	@Test
	public void fanoutSender() throws Exception {
		sender.send();
	}


}