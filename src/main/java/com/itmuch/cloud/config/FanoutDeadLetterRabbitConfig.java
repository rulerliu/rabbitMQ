package com.itmuch.cloud.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 死信队列
 * 
 * 场景：
 * 邮件队列长度已满
 * 消费者拒绝消息
 * 消息已经过期
 * 
 * @author mayn
 *
 */
@Configuration
public class FanoutDeadLetterRabbitConfig {
	
	// 邮件队列
	private static final String FANOUT_DEAD_EMAIL_QUEUE = "fanout_dead_email_queue";

	// 短信队列
	private static final String FANOUT_DEAD_SMS_QUEUE = "fanout_dead_sms_queue";
	// fanout 交换机
	private static final String FANOUT_DEAD_EXCHANGE_NAME = "fanoutDeadExchange";

	/**
	 * 定义死信队列相关信息
	 */
	public final static String DEAD_QUEUE_NAME = "dead_email_queue";
	public final static String DEAD_ROUTING_KEY = "dead_email_routing_key";
	public final static String DEAD_EXCHANGE_NAME = "dead_email_exchange";
	/**
	 * 死信队列 交换机标识符
	 */
	public static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";
	/**
	 * 死信队列交换机绑定键标识符
	 */
	public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

	// 1.定义交换机
	@Bean
	FanoutExchange fanoutDeadExchange() {
		return new FanoutExchange(FANOUT_DEAD_EXCHANGE_NAME);
	}
	
	// 2.定义邮件队列
	@Bean
	public Queue fanoutDeadEamilQueue() {
		// 将邮件队列绑定到死信队列交换机上
		Map<String, Object> map = new HashMap<>(2);
		map.put(DEAD_LETTER_QUEUE_KEY, DEAD_QUEUE_NAME);
		map.put(DEAD_LETTER_ROUTING_KEY, DEAD_ROUTING_KEY);
		Queue queue = new Queue(FANOUT_DEAD_EMAIL_QUEUE, true, false, false, map);
		return queue;
	}

	// 2.定义短信队列
	@Bean
	public Queue fanoutDeadSmsQueue() {
		return new Queue(FANOUT_DEAD_SMS_QUEUE);
	}

	// 3.邮件队列与交换机绑定邮件队列
	@Bean
	Binding bindingExchangeEamil(FanoutExchange fanoutDeadExchange, Queue fanoutDeadEamilQueue) {
		return BindingBuilder.bind(fanoutDeadEamilQueue).to(fanoutDeadExchange);
	}

	// 4.短信队列与交换机绑定短信队列
	@Bean
	Binding bindingExchangeSms(FanoutExchange fanoutDeadExchange, Queue fanoutDeadSmsQueue) {
		return BindingBuilder.bind(fanoutDeadSmsQueue).to(fanoutDeadExchange);
	}

	
	
	// 配置死信队列
	@Bean
	public Queue deadQueue() {
		Queue queue = new Queue(DEAD_QUEUE_NAME, true);
		return queue;
	}

	// 配置死信交换机
	@Bean
	public DirectExchange deadExchange() {
		return new DirectExchange(DEAD_EXCHANGE_NAME);
	}

	// 死信队列与死信交换机绑定
	@Bean
	public Binding bindingDeadExchange(Queue deadQueue, DirectExchange deadExchange) {
		return BindingBuilder.bind(deadQueue).to(deadExchange).with(DEAD_ROUTING_KEY);
	}

}
