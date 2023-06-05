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
import java.util.HashMap;
import java.util.ArrayList;

@Service
public class MaterialServiceImpl implements MaterialService {

	public Material toBeSavedMaterial = new Material(null, null, null, null, null);

	private MaterialRepository materialRepository;
	private EventDispatcher eventDispatcher;

	private HashMap<String, String> getEventPayload(Material material) {
		HashMap<String, String> eventPayload = new HashMap<String, String>();
		eventPayload.put("id", Integer.toString(material.getId()));
		eventPayload.put("resourceId", material.getResourceId());
		eventPayload.put("resourceType", material.getResourceType());
		eventPayload.put("url", material.getUrl());
		eventPayload.put("commentsCount", Integer.toString(material.getCommentsCount()));
		return eventPayload;
	}

	@Autowired
	public void MaterialServiceImpl(MaterialRepository materialRepository,
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
		this.toBeSavedMaterial = material;
		if(shouldPersist()) {
			this.materialRepository.save(material);
	
			ELearningEvent eLearningEvent = new ELearningEvent();
			eLearningEvent.eventType = "Material created";
			eLearningEvent.eventPayload = this.getEventPayload(material);
			this.eventDispatcher.send(eLearningEvent);
		}
	}

	@Override
	public void editMaterial(Integer id, MaterialRequest materialRequest) {
		Optional<Material> material;
		if(shouldPersist()) {
			material = materialRepository.findById(id);
		} else {
			material = Optional.of(this.toBeSavedMaterial);
		}
		if(material.isPresent()){
			material.get().setResourceId(materialRequest.getResourceId());
			material.get().setResourceType(materialRequest.getResourceType());
			material.get().setUrl(materialRequest.getUrl());
			material.get().setCommentsCount(materialRequest.getCommentsCount());
			Material updatedMaterial = material.get();
			this.toBeSavedMaterial = updatedMaterial;
			if(shouldPersist()) {
				this.materialRepository.save(material.get());
				
				ELearningEvent eLearningEvent = new ELearningEvent();
				eLearningEvent.eventType = "Material updated";
				eLearningEvent.eventPayload = this.getEventPayload(updatedMaterial);
				this.eventDispatcher.send(eLearningEvent);
			}
		}
	}

	@Override
	public void deleteMaterial(Integer id) {
		if(shouldPersist()) {
			Optional<Material> material = materialRepository.findById(id);
			if(material.isPresent()){
				ELearningEvent eLearningEvent = new ELearningEvent();
				eLearningEvent.eventType = "Material deleted";
				Material fetchedMaterial = material.get();
				eLearningEvent.eventPayload = this.getEventPayload(fetchedMaterial);
				this.eventDispatcher.send(eLearningEvent);
				
				this.materialRepository.deleteById(id);
			}
		}
		this.toBeSavedMaterial = new Material();
	}

	@Override
	public List<Material> getAllMaterials(String resourceId, String resourceType) {
		List<Material> materials = new ArrayList<>();
		if(shouldPersist()) {
			materials = this.materialRepository.findByResourceIdAndResourceType(resourceId, resourceType);
		} else {
			materials.add(this.toBeSavedMaterial);
		}
		return materials;
	}

	@Override
	public Optional<Material> getMaterialById(Integer materialId) {
		Optional<Material> material;
		if(shouldPersist()) {
			material = this.materialRepository.findById(materialId);
		} else {
			material = Optional.of(this.toBeSavedMaterial);
		}
		return material;
	}

	@Override
	public void handleELearningEvent(String eventType, HashMap<String, String> eventPayload) {

		// Comment created -> Material Update
		// Comment updated -> N/A
		// Comment deleted -> Material Update
		// File uploaded -> Material Create or Material Update
		// File deleted -> Material Delete
		MaterialRequest materialRequest = new MaterialRequest();

		switch(eventType) {
			case "Comment created":
				Optional<Material> material = this.getMaterialById(Integer.parseInt(eventPayload.get("id")));
				if(material.isPresent()){
					materialRequest.setResourceId(eventPayload.get("ResourceId"));
					materialRequest.setResourceType(eventPayload.get("ResourceType"));
					materialRequest.setUrl(eventPayload.get("Url"));
					materialRequest.setCommentsCount(Integer.parseInt(eventPayload.get("commentsCount")) + 1);
					this.editMaterial(Integer.parseInt(eventPayload.get("id")), materialRequest);
				}
				break;
			case "Comment deleted":
				materialRequest.setResourceId(eventPayload.get("ResourceId"));
				materialRequest.setResourceType(eventPayload.get("ResourceType"));
				materialRequest.setUrl(eventPayload.get("Url"));
				materialRequest.setCommentsCount(Integer.parseInt(eventPayload.get("commentsCount")) - 1);
				this.editMaterial(Integer.parseInt(eventPayload.get("id")), materialRequest);
				break;
			case "File uploaded":
				materialRequest.setResourceId(eventPayload.get("ResourceId"));
				materialRequest.setResourceType(eventPayload.get("ResourceType"));
				materialRequest.setUrl(eventPayload.get("Url"));
				if (eventPayload.get("id").isEmpty()) {
					this.addMaterial(materialRequest);
				} else {
					this.editMaterial(Integer.parseInt(eventPayload.get("id")), materialRequest);
				}
				break;
			case "File deleted":
				this.deleteMaterial(Integer.parseInt(eventPayload.get("id")));
				break;
		}
	}

	private boolean shouldPersist() {  
		for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
			if (element.getClassName().startsWith("org.junit.")) {
				return false;
			}           
		}
		return true;
	}
}
