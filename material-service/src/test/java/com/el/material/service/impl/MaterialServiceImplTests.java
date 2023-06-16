package com.el.material.service.impl;

import com.el.material.model.Material;
import com.el.material.model.request.MaterialRequest;
import com.el.material.repository.MaterialRepository;
import com.el.material.event.EventDispatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Assert;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class MaterialServiceImplTests {

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private EventDispatcher eventDispatcher;

    @InjectMocks
    private MaterialServiceImpl materialServiceImpl;

    @Test
    public void shouldAddMaterial() {
        // given
        MaterialRequest material = new MaterialRequest();
        material.setResourceId("1");
        material.setResourceType("class");
        material.setUrl("www.example.com");

        // when
        this.materialServiceImpl.addMaterial(material);

        // then
        Material expectedMaterial = new Material();
        expectedMaterial.setResourceId("1");
        expectedMaterial.setResourceType("class");
        expectedMaterial.setUrl("www.example.com");
        Assert.assertEquals(this.materialServiceImpl.toBeSavedMaterial, expectedMaterial);
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
        this.materialServiceImpl.editMaterial(id, material);

        // then
        Material expectedMaterial = new Material();
        expectedMaterial.setResourceId("1");
        expectedMaterial.setResourceType("class");
        expectedMaterial.setUrl("www.example.com");
        Assert.assertEquals(this.materialServiceImpl.toBeSavedMaterial, expectedMaterial);
    }

    @Test
    public void shouldDeleteMaterial() {
        // given
        Integer id = 1;
        Material expectedMaterial = new Material();
        expectedMaterial.setId(id);
        expectedMaterial.setResourceId("1");
        expectedMaterial.setResourceType("class");
        expectedMaterial.setUrl("www.example.com");
        expectedMaterial.setCommentsCount(1);
    
        // when
        when(materialRepository.findById(id)).thenReturn(Optional.of(expectedMaterial));
        this.materialServiceImpl.deleteMaterial(id);

        // then
        verify(materialRepository).deleteById(eq(id));
        // no exception is thrown
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
        this.materialServiceImpl.toBeSavedMaterial = material;
        List<Material> materials = new ArrayList<>();
        materials = this.materialServiceImpl.getAllMaterials(resourceId, resourceType);

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
        this.materialServiceImpl.toBeSavedMaterial = material;
        Optional<Material> returnedMaterial;
        returnedMaterial = this.materialServiceImpl.getMaterialById(id);

        // then
        Material expectedMaterial = new Material();
        expectedMaterial.setResourceId("1");
        expectedMaterial.setResourceType("class");
        expectedMaterial.setUrl("www.example.com");
        Assert.assertEquals(returnedMaterial, Optional.of(expectedMaterial));
    }

}
