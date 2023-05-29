package com.hnj.product.event;

import com.hnj.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * This class receives the events and triggers the associated
 * business logic.
 */
@Slf4j
@Component
class EventHandler {

    private ProductService productService;

    EventHandler(final ProductService productService) {
        this.productService = productService;
    }

    @RabbitListener(queues = "${inventory.queue}")
    void handleInventoryEvent(final ProductInventoryEvent productInventoryEvent) {
        log.info("Inventory productInventoryEvent received for the product of: {} with quantity: {}", productInventoryEvent.getProductId(), productInventoryEvent.getQuantity());
        try {
            if (productInventoryEvent.getQuantity() >= 0)
                log.info("Updating inventory");
                productService.updateInventory(productInventoryEvent.getProductId(), productInventoryEvent.getQuantity());
        } catch (final Exception e) {
            log.error("Error when trying to add inventory", e);
            // Avoids the productInventoryEvent to be re-queued and reprocessed.
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
