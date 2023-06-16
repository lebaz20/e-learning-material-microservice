package com.el.material.event;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

public class EventHandlerFactory {
    private static Map<ELearningEventType, EventHandlerType> eventHandlerMap;

    @Autowired
    private EventHandlerFactory(List<EventHandlerType> eventHandlers) {
        eventHandlerMap = eventHandlers.stream().collect(Collectors.toMap(EventHandlerType::getType, Function.identity()));
    }

    public static <T> EventHandlerType getEventHandler(ELearningEventType eventType) {
        return Optional.ofNullable(eventHandlerMap.get(eventType)).orElseThrow(IllegalArgumentException::new);
    }
}