package com.el.material.event;

import com.el.material.service.MaterialService;
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

    private MaterialService materialService;

    EventHandler(final MaterialService materialService) {
        this.materialService = materialService;
    }

    @RabbitListener(queues = "${eLearning.queue}")
    void handleELearningEvent(final ELearningEvent eLearningEvent) {
        log.info("ELearning eLearningEvent received for the material of: {} with quantity: {}", eLearningEvent.getMaterialId(), eLearningEvent.getQuantity());
        try {
            // if (eLearningEvent.getQuantity() >= 0)
            //     log.info("Updating eLearning");
            //     materialService.updateELearning(eLearningEvent.getMaterialId(), eLearningEvent.getQuantity());
        } catch (final Exception e) {
            log.error("Error when trying to apply eLearning event", e);
            // Avoids the eLearningEvent to be re-queued and reprocessed.
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
