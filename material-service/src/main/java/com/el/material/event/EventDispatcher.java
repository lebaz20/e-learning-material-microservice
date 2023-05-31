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
    private String eLearningExchange;

    // The routing key to use to send this particular event
    private String eLearningSolvedRoutingKey;

    @Autowired
    EventDispatcher(final RabbitTemplate rabbitTemplate,
                    @Value("${eLearning.exchange}") final String eLearningExchange,
                    @Value("${eLearning.pushed.key}") final String eLearningSolvedRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.eLearningExchange = eLearningExchange;
        this.eLearningSolvedRoutingKey = eLearningSolvedRoutingKey;
    }

    public void send(final ELearningEvent eLearningEvent) {
        rabbitTemplate.convertAndSend(
                eLearningExchange,
                eLearningSolvedRoutingKey,
                eLearningEvent);
    }
}
