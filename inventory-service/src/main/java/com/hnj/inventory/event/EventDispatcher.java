package com.hnj.inventory.event;

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

    // The exchange to use to send anything related to inventory
    private String inventoryExchange;

    // The routing key to use to send this particular event
    private String inventorySolvedRoutingKey;

    @Autowired
    EventDispatcher(final RabbitTemplate rabbitTemplate,
                    @Value("${inventory.exchange}") final String inventoryExchange,
                    @Value("${inventory.pushed.key}") final String inventorySolvedRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.inventoryExchange = inventoryExchange;
        this.inventorySolvedRoutingKey = inventorySolvedRoutingKey;
    }

    public void send(final ProductInventoryEvent productInventoryEvent) {
        rabbitTemplate.convertAndSend(
                inventoryExchange,
                inventorySolvedRoutingKey,
                productInventoryEvent);
    }
}
