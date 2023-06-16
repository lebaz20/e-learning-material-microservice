package com.el.material.event.eventHandler;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.el.material.event.ELearningEventType;
import com.el.material.event.EventHandlerType;
import com.el.material.model.Material;
import com.el.material.model.request.MaterialRequest;
import com.el.material.service.MaterialService;

public class CommentCreated implements EventHandlerType {
    private static final ELearningEventType EVENT_TYPE = ELearningEventType.COMMENT_CREATED;

    private MaterialService materialService;

	@Autowired
	public CommentCreated(MaterialService materialService){
		this.materialService=materialService;
	}

    @Override
    public ELearningEventType getType() {
        return EVENT_TYPE;
    }

    @Override
    public void process(HashMap<String, String> eventPayload) {
        // Comment created -> Material Update
		MaterialRequest materialRequest = new MaterialRequest();

        Optional<Material> material = this.materialService.getMaterialById(Integer.parseInt(eventPayload.get("id")));
        if(material.isPresent()){
            materialRequest.setResourceId(eventPayload.get("ResourceId"));
            materialRequest.setResourceType(eventPayload.get("ResourceType"));
            materialRequest.setUrl(eventPayload.get("Url"));
            materialRequest.setCommentsCount(Integer.parseInt(eventPayload.get("commentsCount")) + 1);
            this.materialService.editMaterial(Integer.parseInt(eventPayload.get("id")), materialRequest);
        }
    }
}