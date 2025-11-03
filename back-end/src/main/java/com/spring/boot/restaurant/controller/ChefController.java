package com.spring.boot.restaurant.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.restaurant.dto.ChefDto;
import com.spring.boot.restaurant.service.ChefService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/chef")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class ChefController {

	private final ChefService chefService;
	
	@GetMapping("/getAll")
	private ResponseEntity<List<ChefDto>> getAllChefs() {
		 List<ChefDto> chefs = chefService.getAllChefs();
		    
		    if (chefs.isEmpty()) {
		        return ResponseEntity.noContent().build(); // 204
		    }
		    
		    return ResponseEntity.ok(chefs); // 200 + data

	}
	
	
	@GetMapping("/getOne/{id}")
	public ResponseEntity<ChefDto> getChefById(@PathVariable Long id) {
	    ChefDto chefDto = chefService.getChefById(id);
	    return ResponseEntity.ok(chefDto);
	}
	
	@PostMapping("/create")
	public ResponseEntity<ChefDto> saveChef(@RequestBody ChefDto chefDto) {
	    ChefDto saved = chefService.saveCategory(chefDto);
	    return ResponseEntity.ok(saved);
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<ChefDto> updateChef(@RequestBody ChefDto chefDto) {
	    ChefDto updated = chefService.updateCategory(chefDto);
	    return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping("deleteOne/{id}")
	public ResponseEntity<String> deleteChef(@PathVariable Long id) {
	    boolean deleted = chefService.deleteChef(id);

	    if (deleted) {
	        return ResponseEntity.ok("Chef deleted successfully ✅");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body("Chef not found ❌");
	    }
	}

	
	@DeleteMapping("/deleteList")
	public ResponseEntity<String> deleteMultipleChefs(@RequestBody List<Long> ids) {
	    boolean deleted = chefService.deleteChefsByIds(ids);

	    if (deleted) {
	        return ResponseEntity.ok("All selected chefs were deleted successfully ✅");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                             .body("No chefs found for the given IDs ❌");
	    }
	}


}
