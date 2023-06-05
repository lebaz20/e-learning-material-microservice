package com.el.material.service.impl;

import com.el.material.model.Material;
import com.el.material.model.request.MaterialRequest;
import com.el.material.repository.MaterialRepository;
import com.el.material.event.EventDispatcher;
import com.el.material.service.impl.MaterialServiceImpl;
import org.junit.Test;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import com.el.material.model.request.MaterialRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class MaterialServiceImplTests {

    @Test
    public void shouldAddMaterial() {
        // given
        MaterialRequest material = new MaterialRequest();
        material.setResourceId("1");
        material.setResourceType("class");
        material.setUrl("www.example.com");

        // when
        MaterialServiceImpl materialServiceImpl = new MaterialServiceImpl();
        materialServiceImpl.addMaterial(material);

        // then
        Material expectedMaterial = new Material();
        expectedMaterial.setResourceId("1");
        expectedMaterial.setResourceType("class");
        expectedMaterial.setUrl("www.example.com");
        Assert.assertEquals(materialServiceImpl.toBeSavedMaterial, expectedMaterial);
    }

    @Test
    public void shouldEditMaterial() {
        // given
        MaterialRequest material = new MaterialRequest();
        material.setResourceId("1");
        material.setResourceType("class");
        material.setUrl("www.example.com");
        Integer id = 1;
    
        // when
        MaterialServiceImpl materialServiceImpl = new MaterialServiceImpl();
        materialServiceImpl.editMaterial(id, material);

        // then
        Material expectedMaterial = new Material();
        expectedMaterial.setResourceId("1");
        expectedMaterial.setResourceType("class");
        expectedMaterial.setUrl("www.example.com");
        Assert.assertEquals(materialServiceImpl.toBeSavedMaterial, expectedMaterial);
    }

    @Test
    public void shouldDeleteMaterial() {
        // given
        Integer id = 1;
    
        // when
        MaterialServiceImpl materialServiceImpl = new MaterialServiceImpl();
        materialServiceImpl.deleteMaterial(id);

        // then
        Material expectedMaterial = new Material();
        Assert.assertEquals(materialServiceImpl.toBeSavedMaterial, expectedMaterial);
    }

    @Test
    public void shouldGetAllMaterials() {
        // given
        Material material = new Material();
        String resourceId = "1";
        String resourceType = "class";
        material.setResourceId(resourceId);
        material.setResourceType(resourceType);
        material.setUrl("www.example.com");
        
        // when
        MaterialServiceImpl materialServiceImpl = new MaterialServiceImpl();
        materialServiceImpl.toBeSavedMaterial = material;
        List<Material> materials = new ArrayList<>();
        materials = materialServiceImpl.getAllMaterials(resourceId, resourceType);

        // then
        List<Material> expectedMaterials = new ArrayList<>();
        expectedMaterials.add(material);
        Assert.assertEquals(materials, expectedMaterials);
    }

    @Test
    public void shouldGetMaterial() {
        // given
        Material material = new Material();
        String resourceId = "1";
        String resourceType = "class";
        material.setResourceId(resourceId);
        material.setResourceType(resourceType);
        material.setUrl("www.example.com");
        Integer id = 1;
        
        // when
        MaterialServiceImpl materialServiceImpl = new MaterialServiceImpl();
        materialServiceImpl.toBeSavedMaterial = material;
        Optional<Material> returnedMaterial;
        returnedMaterial = materialServiceImpl.getMaterialById(id);

        // then
        Material expectedMaterial = new Material();
        expectedMaterial.setResourceId("1");
        expectedMaterial.setResourceType("class");
        expectedMaterial.setUrl("www.example.com");
        Assert.assertEquals(returnedMaterial, Optional.of(expectedMaterial));
    }

}
