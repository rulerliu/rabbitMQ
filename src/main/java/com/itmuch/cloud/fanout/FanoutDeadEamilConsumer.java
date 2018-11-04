package com.itmuch.cloud.fanout;

import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itmuch.cloud.util.HttpClientUtils;
import com.rabbitmq.client.Channel;

/**
 * 死信队列
 * 场景：
 * 邮件队列长度已满
 * 消费者拒绝消息
 * 消息已经过期
 */
@Component
public class FanoutDeadEamilConsumer {
	
	
	/**
	 * @param message
	 * @throws Exception
	 */
	@RabbitListener(queues = "fanout_dead_email_queue")
	public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws Exception {
		String msg = new String(message.getBody(), "utf-8");
		String msgId = message.getMessageProperties().getMessageId();
		System.out.println("邮件消费者获取生产者消息msg:" + msg + ",消息id：" + msgId);
		
		// case1
		JSONObject jsonObject = JSON.parseObject(msg);
		Integer num = jsonObject.getInteger("num");
		// 如果调用第三方接口无法访问，如何实现自动重试
		try {
			int result = 1 / num;
			System.out.println("result:" + result);
			
			// 手动ack
			Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
			// 手动签收
			channel.basicAck(deliveryTag, false);
		} catch (Exception e) {
			e.printStackTrace();
			// 拒绝消费消息（消息丢失）给死信队列
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
		}

		
		// case2
		// int i = 1 / 0;
	}
	
}
