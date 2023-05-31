package com.el.material.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import java.util.HashMap;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class ELearningEvent {
    public String eventType;
    public HashMap<String, String> eventPayload;
}
