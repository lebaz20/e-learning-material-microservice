package com.el.material.repository;

import com.el.material.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    List<Material> findByResourceIdAndResourceType(String resourceId, String resourceType);
}
