package com.itmuch.cloud.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 队列配置
 * @author mayn
 *
 */
@Configuration
public class RabbitConfig {
	
	@Bean
	public Queue queue () {
		return new Queue("hello");
	}
	
	@Bean
    public Queue neoQueue() {
        return new Queue("neo");
    }
	
	@Bean
    public Queue objectQueue() {
        return new Queue("object");
    }
	
}
