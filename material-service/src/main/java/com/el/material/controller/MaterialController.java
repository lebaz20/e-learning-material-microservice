package com.el.material.controller;

import com.el.material.model.Material;
import com.el.material.model.request.MaterialRequest;
import com.el.material.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins="*", allowedHeaders="*")
public class MaterialController {
	private MaterialService materialService;

	@Autowired
	public MaterialController(MaterialService materialService){
		this.materialService=materialService;
	}

	@PostMapping("/materials")
	public void addMaterial(@Valid @RequestBody MaterialRequest materialRequest){
		materialRequest.setCommentsCount(0);
		this.materialService.addMaterial(materialRequest);
	}

	@GetMapping("/materials")
    public List<Material> getAllMaterials(@RequestParam String resourceId, @RequestParam String resourceType){
	    return this.materialService.getAllMaterials(resourceId, resourceType);
    }

    @GetMapping("/materials/{id}")
	public Material getMaterial(@PathVariable Integer id){
		return this.materialService.getMaterialById(id).isPresent() ? materialService.getMaterialById(id).get() : null;
	}

	@PutMapping("/materials/{id}")
	public void editMaterial(@PathVariable Integer id, @Valid @RequestBody MaterialRequest materialRequest){
		materialRequest.setCommentsCount(0);
		this.materialService.editMaterial(id, materialRequest);
	}

	@DeleteMapping("/materials/{id}")
	public void deleteMaterial(@PathVariable Integer id){
		this.materialService.deleteMaterial(id);
	}
}
