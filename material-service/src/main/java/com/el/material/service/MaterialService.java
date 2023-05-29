package com.el.material.service;

import com.el.material.model.Material;
import com.el.material.model.request.MaterialRequest;

import java.util.List;
import java.util.Optional;

public interface MaterialService {
    Material addMaterial(MaterialRequest materialRequest);
    List<Material> getAllMaterials();
    Optional<Material> getMaterialById(Integer materialId);
    void updateELearning(Integer materialId, Integer quantity);
    Material editMaterial(Integer id, MaterialRequest materialRequest);
    void deleteMaterial(Integer id);
}
