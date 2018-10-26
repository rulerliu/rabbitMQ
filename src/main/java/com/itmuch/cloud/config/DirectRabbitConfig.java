package com.itmuch.cloud.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * topic 是RabbitMQ中最灵活的一种方式，可以根据routing_key自由的绑定不同的队列
 * 首先对topic规则配置，这里使用两个队列来测试
 * @author mayn
 *
 */
@Configuration
public class DirectRabbitConfig {

	final static String message = "topic.message";
    final static String messages = "topic.messages";
 
    @Bean
    public Queue queueMessage() {
        return new Queue(DirectRabbitConfig.message);
    }
 
    @Bean
    public Queue queueMessages() {
        return new Queue(DirectRabbitConfig.messages);
    }
 
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("exchange");
    }
 
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }
 
    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }

    
    
    
    //-----------------------------------------------------
    
    public static final String EXCHANGE   = "spring-topic-exchange";
    //*表示一个词, #表示零个或多个词
    public static final String ROUTINGKEY1 = "weather.routingKey.*";
    public static final String ROUTINGKEY2 = "msg.routingKey.#";
    public static final String ROUTINGKEY3 = "*.routingKey";
    
    @Bean
    public TopicExchange topicExchange2() {
        return new TopicExchange(EXCHANGE);
    }
    
    @Bean
    public Queue queue() {
        return new Queue("spring-queue", true); //队列持久
    }
    @Bean
    public Queue queue2() {
        return new Queue("spring-queue2", true); //队列持久
    }
    
    @Bean
    public Binding binding(Queue queue) {
        return BindingBuilder.bind(queue).to(topicExchange2()).with(ROUTINGKEY1);
    }
    @Bean
    public Binding binding2(Queue queue2) {
        return BindingBuilder.bind(queue2).to(topicExchange2()).with(ROUTINGKEY2);
    }
    @Bean
    public Binding binding3(Queue queue2) {
        return BindingBuilder.bind(queue2).to(topicExchange2()).with(ROUTINGKEY3);
    }
	
}
