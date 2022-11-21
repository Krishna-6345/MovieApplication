package com.niit.product.config;

import com.niit.product.domain.WatchedDTO;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    RabbitTemplate rabbitTemplate;
    DirectExchange exchange;

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate, DirectExchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public void sendMessageToRabbitTemplate(WatchedDTO watchedDTO){
        rabbitTemplate.convertAndSend(exchange.getName(),"watched_routing",watchedDTO);
    }


}
