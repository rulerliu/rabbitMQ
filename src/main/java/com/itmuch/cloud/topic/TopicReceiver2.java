package com.itmuch.cloud.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.messages")
public class TopicReceiver2 {

    @RabbitHandler
    public void process(String message) {
        System.out.println("topic.messages received : " + message);
    }

}

