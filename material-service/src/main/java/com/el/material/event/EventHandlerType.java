package com.el.material.event;

import java.util.HashMap;

public interface EventHandlerType {
    ELearningEventType getType();
    void process(HashMap<String, String> eventPayload);
}