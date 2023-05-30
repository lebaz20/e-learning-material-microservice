package com.el.material.service;

import com.el.material.model.Material;
import com.el.material.model.request.MaterialRequest;

import java.util.List;
import java.util.Optional;

public interface MaterialService {
    void addMaterial(MaterialRequest materialRequest);
    List<Material> getAllMaterials();
    Optional<Material> getMaterialById(Integer materialId);
    void handleELearningEvent(String eventType, HashMap<String, Object> eventPayload);
    void editMaterial(Integer id, MaterialRequest materialRequest);
    void deleteMaterial(Integer id);
}
