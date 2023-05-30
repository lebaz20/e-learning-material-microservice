package com.el.material.service.impl;

import com.el.material.model.Material;
import com.el.material.model.request.MaterialRequest;
import com.el.material.repository.MaterialRepository;
import com.el.material.service.MaterialService;
import com.el.material.event.EventDispatcher;
import com.el.material.event.ELearningEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialServiceImpl implements MaterialService {

	private final MaterialRepository materialRepository;
	private EventDispatcher eventDispatcher;

	private HashMap<String, Object> getEventPayload(Material material) {
		HashMap<String, Object> eventPayload = new HashMap<String, Object>();
		eventPayload.put("id", material.getId());
		eventPayload.put("resourceId", material.getResourceId());
		eventPayload.put("resourceType", material.getResourceType());
		eventPayload.put("url", material.getUrl());
		eventPayload.put("commentsCount", material.getCommentsCount());
		return eventPayload;
	}

	@Autowired
	public InventoryServiceImpl(MaterialRepository materialRepository,
							EventDispatcher eventDispatcher) {
		this.materialRepository = materialRepository;
		this.eventDispatcher = eventDispatcher;
	}

	@Override
	public void addMaterial(MaterialRequest materialRequest) {
		Material material = new Material().builder()
				.resourceId(materialRequest.getResourceId())
				.resourceType(materialRequest.getResourceType())
				.commentsCount(materialRequest.getCommentsCount())
				.url(materialRequest.getUrl())
				.build();

		this.materialRepository.save(material)

		this.eventDispatcher.send(new ELearningEvent("Material created", this.getEventPayload(material)));
	}

	@Override
	public void editMaterial(Integer id, MaterialRequest materialRequest) {
		Optional<Material> material = materialRepository.findById(id);
		if(material.isPresent()){
			material.get().setResourceId(materialRequest.getResourceId());
			material.get().setResourceType(materialRequest.getResourceType());
			material.get().setUrl(materialRequest.getUrl());
			material.get().setCommentsCount(materialRequest.getCommentsCount());
			
			this.materialRepository.save(material.get());
			
			this.eventDispatcher.send(new ELearningEvent("Material updated", this.getEventPayload(material)));
		}
	}

	@Override
	public void deleteMaterial(Integer id) {
		Optional<Material> material = materialRepository.findById(id);
		if(material.isPresent()){
			this.eventDispatcher.send(new ELearningEvent("Material deleted", this.getEventPayload(material)));
			
			this.materialRepository.deleteById(id);
		}
	}

	@Override
	public List<Material> getAllMaterials(String resourceId, String resourceType) {
		return this.materialRepository.findByResourceIdAndResourceType(resourceId, resourceType);
	}

	@Override
	public Optional<Material> getMaterialById(Integer materialId) {
		return this.materialRepository.findById(materialId);
	}

	@Override
	public void handleELearningEvent(String eventType, HashMap<String, Object> eventPayload) {

		// Comment created -> Material Update
		// Comment updated -> N/A
		// Comment deleted -> Material Update
		// File uploaded -> Material Create or Material Update
		// File deleted -> Material Delete

		switch(eventType) {
			case "Comment created":
				Material material = this.getMaterialById(eventPayload.get("id"));
				MaterialRequest materialRequest = new MaterialRequest();
				materialRequest.setResourceId(eventPayload.get("ResourceId"));
				materialRequest.setResourceType(eventPayload.get("ResourceType"));
				materialRequest.setUrl(eventPayload.get("Url"));
				materialRequest.setCommentsCount(eventPayload.get("commentsCount") + 1);
				this.editMaterial(eventPayload.get("id"), materialRequest);
				break;
			case "Comment deleted":
				MaterialRequest materialRequest = new MaterialRequest();
				materialRequest.setResourceId(eventPayload.get("ResourceId"));
				materialRequest.setResourceType(eventPayload.get("ResourceType"));
				materialRequest.setUrl(eventPayload.get("Url"));
				materialRequest.setCommentsCount(eventPayload.get("commentsCount") - 1);
				this.editMaterial(eventPayload.get("id"), materialRequest);
				break;
			case "File uploaded":
				MaterialRequest materialRequest = new MaterialRequest();
				materialRequest.setResourceId(eventPayload.get("ResourceId"));
				materialRequest.setResourceType(eventPayload.get("ResourceType"));
				materialRequest.setUrl(eventPayload.get("Url"));
				materialRequest.setCommentsCount(eventPayload.get("commentsCount") - 1);
				if (eventPayload.get("id")) {
					this.editMaterial(eventPayload.get("id"), materialRequest);
				} else {
					this.addMaterial(materialRequest);
				}
				break;
			case "File deleted":
				this.deleteMaterial(eventPayload.get("id"));
				break;
		}
	}
}
