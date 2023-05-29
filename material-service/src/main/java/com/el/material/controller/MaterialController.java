package com.el.material.controller;

import com.el.material.model.Material;
import com.el.material.model.request.MaterialRequest;
import com.el.material.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:3000", allowedHeaders="*")
public class MaterialController {
	private MaterialService materialService;

	@Autowired
	public MaterialController(MaterialService materialService){
		this.materialService=materialService;
	}

	@PostMapping("/materials")
	public Material addMaterial(@Valid @RequestBody MaterialRequest materialRequest){
		return materialService.addMaterial(materialRequest);
	}

	@GetMapping("/materials")
    public List<Material> getAllMaterials(@QueryParam("resourceId") String resourceId, @QueryParam("resourceType") String resourceType){
	    return materialService.getAllMaterials(resourceId, resourceType);
    }

    @GetMapping("/materials/{id}")
	public Material getMaterial(@PathVariable Integer id){
		return materialService.getMaterialById(id).isPresent() ? materialService.getMaterialById(id).get() : null;
	}

	@PutMapping("/materials/{id}")
	public Material editMaterial(@PathVariable Integer id, @Valid @RequestBody MaterialRequest materialRequest){
		return materialService.editMaterial(id, materialRequest);
	}

	@DeleteMapping("/materials/{id}")
	public void deleteMaterial(@PathVariable Integer id){
		materialService.deleteMaterial(id);
	}
}
