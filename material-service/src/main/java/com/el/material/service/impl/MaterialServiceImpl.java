package com.el.material.service.impl;

import com.el.material.model.Material;
import com.el.material.model.request.MaterialRequest;
import com.el.material.repository.MaterialRepository;
import com.el.material.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialServiceImpl implements MaterialService {

	private final MaterialRepository materialRepository;

	@Autowired
	public MaterialServiceImpl(MaterialRepository materialRepository) {
		this.materialRepository = materialRepository;
	}

	@Override
	public Material addMaterial(MaterialRequest materialRequest) {
		Material material = new Material().builder()
				.resourceId(materialRequest.getResourceId())
				.resourceType(materialRequest.getResourceType())
				.url(materialRequest.getUrl())
				.build();

		return materialRepository.save(material);
	}

	@Override
	public Material editMaterial(Integer id, MaterialRequest materialRequest) {
		Optional<Material> material = materialRepository.findById(id);
		if(material.isPresent()){
			material.get().setResourceId(materialRequest.getResourceId());
			material.get().setResourceType(materialRequest.getResourceType());
			material.get().setUrl(materialRequest.getUrl());
			return materialRepository.save(material.get());
		} else {
			return null;
		}
	}

	@Override
	public void deleteMaterial(Integer id) {
		Optional<Material> material = materialRepository.findById(id);
		if(material.isPresent()){
			materialRepository.deleteById(id);
		}
	}

	@Override
	public List<Material> getAllMaterials(String resourceId, String resourceType) {
		return materialRepository.findByResourceIdAndResourceType(resourceId, resourceType);
	}

	@Override
	public Optional<Material> getMaterialById(Integer materialId) {
		return materialRepository.findById(materialId);
	}

	@Override
	public void updateELearning(Integer materialId, Integer quantity) {
		Optional<Material> material = getMaterialById(materialId);
		if(material.isPresent()){
			material.get().setQuantity(quantity);
			materialRepository.save(material.get());
		}
	}
}
