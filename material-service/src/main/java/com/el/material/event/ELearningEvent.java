package com.el.material.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import java.util.HashMap;
import java.io.Serializable;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class ELearningEvent implements Serializable {
    public String eventType;
    public HashMap<String, String> eventPayload;
}
