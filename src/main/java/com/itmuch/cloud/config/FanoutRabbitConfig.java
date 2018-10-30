package com.itmuch.cloud.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。
 * 
 * @author mayn
 *
 */
@Configuration
public class FanoutRabbitConfig {

	// 邮件队列
	private static final String FANOUT_EMAIL_QUEUE = "fanout_eamil_queue";

	// 短信队列
	private static final String FANOUT_SMS_QUEUE = "fanout_sms_queue";

	// 交换机名称
	private static final String EXCHANGE_NAME = "fanoutExchange";

	// 定义交换机
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange(EXCHANGE_NAME);
	}

	// 定义邮件队列
	@Bean
	public Queue fanoutEmailQueue() {
		return new Queue(FANOUT_EMAIL_QUEUE);
	}

	// 定义短信队列
	@Bean
	public Queue fanoutSmsQueue() {
		return new Queue(FANOUT_SMS_QUEUE);
	}

	// 队列与交换机绑定
	@Bean
	Binding bindingExchangeEmail(Queue fanoutEmailQueue, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(fanoutEmailQueue).to(fanoutExchange);
	}

	// 队列与交换机绑定
	@Bean
	Binding bindingExchangeSms(Queue fanoutSmsQueue, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(fanoutSmsQueue).to(fanoutExchange);
	}

}
