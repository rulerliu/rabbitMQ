package com.itmuch.cloud.fanout;

import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itmuch.cloud.util.HttpClientUtils;
import com.rabbitmq.client.Channel;

/**
 * @RabbitListener 底层使用AOP进行拦截，如果出现没有抛出异常，自动提交事务
 * 如果AOP使用异常通知拦截，获取异常信息的话， 自动实现补偿机制，该消息会缓存到rabbitMQ服务器端存放，一直重试到不抛异常为止
 * 修改重试机制策略，一般默认情况下，间隔5s重试一次
 * @author mayn
 *
 */
@Component
public class FanoutEamilConsumer {
	
	/**
	 * rabbitMQ 默认情况下，如果消费者程序出现异常的情况下，会自动实现补偿机制
	 * 补偿（重试机制）：  队列服务器  发送补偿请求
	 * 如果消费端，程序业务逻辑出现异常消息会消费成功吗？不会
	 * 邮件消费者程序出现异常，不会影响到短信消费者正常消费
	 * @param msg
	 * @throws Exception
	 */
	@RabbitListener(queues = "fanout_eamil_queue")
	public void process(String msg) throws Exception {
		System.out.println("邮件消费者获取生产者消息msg:" + msg);
		
		// case1
		JSONObject jsonObject = JSON.parseObject(msg);
		String email = jsonObject.getString("email");
		String emailUrl = "http://127.0.0.1:8083/sendEmail?email=" + email;
		JSONObject result = HttpClientUtils.httpGet(emailUrl);
		// 如果调用第三方接口无法访问，如何实现自动重试
		if (result == null) {
			throw new Exception("调用第三方邮件接口失败...");
		}
		System.out.println("邮件消费者结束调用第三方接口成功：result：" + result);
		
		// case2
		// int i = 1 / 0;
	}
	
	// 如何合适选择重试机制？
	// case1：消费者获取到消息之后，调用第三方接口，但接口暂时无法访问，是否需要重试？需要
	// case2：消费者获取到消息之后，跑出程序转换异常，是否需要重试？不需要
	// 对于case2，如果消费者代码抛出异常是要发布新版本才可以解决问题的，那么不需要重试，重试也无济于事
	// 应该采用日志记录 + 定时任务job健康检查 + 人工进行补偿
	
	
	
	
	/**
	 * MessageID，手动签收模式
	 * @param message
	 * @throws Exception
	 */
	@RabbitListener(queues = "fanout_eamil_queue2")
	public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws Exception {
		String msg = new String(message.getBody(), "utf-8");
		String msgId = message.getMessageProperties().getMessageId();
		System.out.println("邮件消费者获取生产者消息msg:" + msg + ",消息id：" + msgId);
		
		// case1
		JSONObject jsonObject = JSON.parseObject(msg);
		String email = jsonObject.getString("email");
		String emailUrl = "http://127.0.0.1:8083/sendEmail?email=" + email;
		JSONObject result = HttpClientUtils.httpGet(emailUrl);
		// 如果调用第三方接口无法访问，如何实现自动重试
		if (result == null) {
			throw new Exception("调用第三方邮件接口失败...");
		}
		System.out.println("邮件消费者结束调用第三方接口成功：result：" + result);
		
		// 手动ack
		Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
		// 手动签收
		channel.basicAck(deliveryTag, false);

		
		// case2
		// int i = 1 / 0;
	}
	
}
