package com.itmuch.cloud.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * topic 是RabbitMQ中最灵活的一种方式，可以根据routing_key自由的绑定不同的队列，*代表一个字符，#代表多个
 * 首先对topic规则配置，这里使用两个队列来测试
 * @author mayn
 *
 */
@Configuration
public class TopicRabbitConfig {

	final static String TOPIC_MESSAGE_QUQUE = "topic.message";
    final static String TOPIC_MESSAGES_QUQUE = "topic.messages";
    final static String TOPIC_MESSAGES2_QUQUE = "topic.messages2";
    
    // 交换机名称
 	private static final String EXCHANGE_NAME = "topicExchange";
 
    @Bean
    public Queue queueMessage() {
        return new Queue(TOPIC_MESSAGE_QUQUE);
    }
 
    @Bean
    public Queue queueMessages() {
        return new Queue(TOPIC_MESSAGES_QUQUE);
    }
    
    @Bean
    public Queue queueMessages2() {
    	return new Queue(TOPIC_MESSAGES2_QUQUE);
    }
 
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }
 
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }
 
    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }
    
    @Bean
    Binding bindingExchangeMessages2(Queue queueMessages2, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages2).to(exchange).with("topic.*");
    }
	
}
