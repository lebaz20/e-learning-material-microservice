package com.el.material.event.eventHandler;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.el.material.event.ELearningEventType;
import com.el.material.event.EventHandlerType;
import com.el.material.model.Material;
import com.el.material.service.MaterialService;

public class FileDeleted implements EventHandlerType {
    private static final ELearningEventType EVENT_TYPE = ELearningEventType.FILE_DELETED;

    private MaterialService materialService;

	@Autowired
	public FileDeleted(MaterialService materialService){
		this.materialService=materialService;
	}

    @Override
    public ELearningEventType getType() {
        return EVENT_TYPE;
    }

    @Override
    public void process(HashMap<String, String> eventPayload) {
        // File deleted -> Material Delete
		Optional<Material> material = this.materialService.getMaterialById(Integer.parseInt(eventPayload.get("id")));
        if(material.isPresent()){
            this.materialService.deleteMaterial(Integer.parseInt(eventPayload.get("id")));
        }
    }
}