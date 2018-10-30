package com.itmuch.cloud.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author mayn
 *
 */
@Configuration
public class DirectRabbitConfig {

	// 邮件路由键名称
	private static final String DIRECT_EMAIL_ROUTING_KEY = "direct_email_routing_key";

	// 短信路由键名称
	private static final String DIRECT_SMS_ROUTING_KEY = "direct_sms_routing_key";

	// 邮件队列
	private static final String DIRECT_EMAIL_QUEUE = "direct_eamil_queue";

	// 短信队列
	private static final String DIRECT_SMS_QUEUE = "direct_sms_queue";

	// 交换机名称
	private static final String DIRECT_EXCHANGE_NAME = "directExchange";

	// 定义交换机
	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange(DIRECT_EXCHANGE_NAME);
	}

	// 定义邮件队列
	@Bean
	public Queue directEmailQueue() {
		return new Queue(DIRECT_EMAIL_QUEUE, true);// 队列持久
	}

	// 定义短信队列
	@Bean
	public Queue directSmsQueue() {
		return new Queue(DIRECT_SMS_QUEUE, true);// 队列持久
	}

	// 队列与交换机绑定
	@Bean
	Binding bindingExchangeEmail() {
		return BindingBuilder.bind(directEmailQueue()).to(directExchange()).with(DIRECT_EMAIL_ROUTING_KEY);
	}

	// 队列与交换机绑定
	@Bean
	Binding bindingExchangeSms(Queue directSmsQueue, DirectExchange directExchange) {
		return BindingBuilder.bind(directSmsQueue()).to(directExchange()).with(DIRECT_SMS_ROUTING_KEY);
	}

	/*
	 * // 队列与交换机绑定
	 * 
	 * @Bean Binding bindingExchangeEmail(Queue directEmailQueue, DirectExchange
	 * directExchange) { return
	 * BindingBuilder.bind(directEmailQueue).to(directExchange).with(
	 * DIRECT_EMAIL_ROUTING_KEY); }
	 * 
	 * // 队列与交换机绑定
	 * 
	 * @Bean Binding bindingExchangeSms(Queue directSmsQueue, DirectExchange
	 * directExchange) { return
	 * BindingBuilder.bind(directSmsQueue).to(directExchange).with(
	 * DIRECT_SMS_ROUTING_KEY); }
	 */

}
