package com.el.material.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Handles the communication with the Event Bus.
 */
@Component
public class EventDispatcher {

    private RabbitTemplate rabbitTemplate;

    // The exchange to use to send anything related to material
    private String materialExchange;

    // The routing key to use to send this particular event
    private String materialSolvedRoutingKey;

    @Autowired
    EventDispatcher(final RabbitTemplate rabbitTemplate,
                    @Value("${material.exchange}") final String materialExchange,
                    @Value("${material.pushed.key}") final String materialSolvedRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.materialExchange = materialExchange;
        this.materialSolvedRoutingKey = materialSolvedRoutingKey;
    }

    public void send(final ELearningEvent eLearningEvent) {
        rabbitTemplate.convertAndSend(
                materialExchange,
                materialSolvedRoutingKey,
                eLearningEvent);
    }
}
