package com.spring.boot.restaurant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.boot.restaurant.dto.ChefDto;
import com.spring.boot.restaurant.exception.BadRequestException;
import com.spring.boot.restaurant.exception.NotFoundException;
import com.spring.boot.restaurant.mapper.ChefMapper;
import com.spring.boot.restaurant.model.Chef;
import com.spring.boot.restaurant.repository.ChefRepo;
import com.spring.boot.restaurant.service.ChefService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChefServiseImpl implements ChefService
{

	private final ChefRepo chefRepo;
	
	private final ChefMapper chefMapper;
	
	
	@Override
	public List<ChefDto> getAllChefs() {
		List<Chef> chefs = chefRepo.findAll();
	    return chefs.stream()
	                .map(chefMapper::toDto)
	                .toList();
	}

	@Override
	public ChefDto getChefById(Long id) {
		Chef chef = chefRepo.findById(id)
	            .orElseThrow(() -> new NotFoundException("Chef not found with ID: " + id));
		
	    return chefMapper.toDto(chef);
	}

	@Override
	public ChefDto saveCategory(ChefDto chefDto) {
		// 1️⃣ Check if the ID is null → means we are adding a new Chef
	    if (chefDto.getId() != null) {
	    	throw new BadRequestException("Id must be null ");
	        
	    }

	 // Create a new Chef entity from DTO
        Chef newChef = chefMapper.toEntity(chefDto);
        
        // Save it in the database
        Chef savedChef = chefRepo.save(newChef);
        
        // Return the saved entity as a DTO
        return chefMapper.toDto(savedChef);

	}

	@Override
	public ChefDto updateCategory(ChefDto chefDto) {
		// 1️⃣ Validate that the DTO has an ID
	    if (chefDto.getId() == null) {
	        throw new BadRequestException("Chef ID must not be null for update operation.");
	    }

	    // 2️⃣ Find the existing Chef in the database
	    Chef existingChef = chefRepo.findById(chefDto.getId())
	            .orElseThrow(() -> new NotFoundException("Chef not found with ID: " + chefDto.getId()));

	    // 3️⃣ Update the existing entity with new values from DTO
	    existingChef.setName(chefDto.getName());
	    existingChef.setSpecialty(chefDto.getSpecialty());
	    existingChef.setLogoPath(chefDto.getLogoPath());
	    existingChef.setFaceLink(chefDto.getFaceLink());
	    existingChef.setTweLink(chefDto.getTweLink());
	    existingChef.setInstaLink(chefDto.getInstaLink());

	    // 4️⃣ Save the updated entity
	    Chef updatedChef = chefRepo.save(existingChef);

	    // 5️⃣ Convert the updated entity back to DTO
	    return chefMapper.toDto(updatedChef);
	}

	@Override
	public boolean deleteChef(Long id) {
		// 1️⃣ Check if the Chef exists in the database
	    if (!chefRepo.existsById(id)) {
	        // If the Chef does not exist, return false or throw a custom exception
	        throw new NotFoundException("Chef not found with ID: " + id);
	        // return false; // (optional alternative)
	    }

	    // 2️⃣ Delete the Chef by ID
	    chefRepo.deleteById(id);

	    // 3️⃣ Return true to confirm successful deletion
	    return true;
	}

	@Override
	public boolean deleteChefsByIds(List<Long> ids) {
		// 1️⃣ Validate input list
	    if (ids == null || ids.isEmpty()) {
	        throw new BadRequestException("Chef ID list must not be null or empty.");
	    }

	    // 2️⃣ Fetch all chefs that exist in the given list of IDs
	    List<Chef> chefsToDelete = chefRepo.findAllById(ids);

	    // 3️⃣ Check if any Chef IDs were not found
	    if (chefsToDelete.isEmpty()) {
	        throw new NotFoundException("No chefs found for the provided IDs.");
	    }

	    // 4️⃣ Perform the delete operation
	    chefRepo.deleteAll(chefsToDelete);

	    // 5️⃣ Return true to confirm successful deletion
	    return true;
	}

}
