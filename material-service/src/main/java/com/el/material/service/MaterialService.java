package com.el.material.service;

import com.el.material.model.Material;
import com.el.material.model.request.MaterialRequest;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;

public interface MaterialService {
    void addMaterial(MaterialRequest materialRequest);
    List<Material> getAllMaterials(String resourceId, String resourceType);
    Optional<Material> getMaterialById(Integer materialId);
    void handleELearningEvent(String eventType, HashMap<String, String> eventPayload);
    void editMaterial(Integer id, MaterialRequest materialRequest);
    void deleteMaterial(Integer id);
}
