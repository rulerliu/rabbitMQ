package com.itmuch.cloud.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DirectRabbitmqConfig {

	// 派单队列
	public static final String DIRECT_EMAIL_QUQUE = "direct_email_queue";
	// 补单队列，判断订单是否已经被创建
	public static final String DIRECT_SMS_QUEUE = "direct_sms_queue";
	// 派单交换机
	private static final String DIRECT_EXCHANGE_NAME = "directExchange";

	// 1.定义订单队列
	@Bean
	public Queue directEmailQueue() {
		return new Queue(DIRECT_EMAIL_QUQUE);
	}

	// 2.定义补订单队列
	@Bean
	public Queue directSmsQueue() {
		return new Queue(DIRECT_SMS_QUEUE);
	}

	// 2.定义交换机
	@Bean
	DirectExchange directExchange() {
		return new DirectExchange(DIRECT_EXCHANGE_NAME);
	}

	// 3.派单队列与交换机绑定
	@Bean
	Binding bindingExchangeOrderDicQueue() {
		return BindingBuilder.bind(directEmailQueue()).to(directExchange()).with("direct.email.routing.key");
	}

	// 3.补单队列与交换机绑定
	@Bean
	Binding bindingExchangeCreateOrder() {
		return BindingBuilder.bind(directSmsQueue()).to(directExchange()).with("direct.sms.routing.key");
	}

}