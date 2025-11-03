package com.spring.boot.restaurant.service;

import com.spring.boot.restaurant.dto.ChefDto;

import java.util.List;

public interface ChefService {
    /**
     * Retrieve all Chefs.
     *
     * @return a list of all Chefs DTOs
     */
    List<ChefDto> getAllChefs();

    /**
     * Retrieve a chef by its ID.
     *
     * @param id the ID of the chef
     * @return the corresponding chef DTO
     */
    ChefDto getChefById(Long id);

    /**
     * Save a new chef.
     *
     * @param chefDto the chef data to save
     * @return the saved chef DTO
     */
    ChefDto saveCategory(ChefDto chefDto);

    /**
     * Update an existing chef.
     *
     * @param ChefDto the updated chef data
     * @return the updated chef DTO
     */
    ChefDto updateCategory(ChefDto chefDto);

    /**
     * Delete a chef by its ID.
     *
     * @param id the ID of the chef to delete
     * @return true if deletion was successful, false otherwise
     */
    boolean deleteChef(Long id);

    /**
     * Delete multiple chefs by their IDs.
     *
     * @param ids a list of chefs IDs to delete
     * @return true if deletion was successful, false otherwise
     */
    boolean deleteChefsByIds(List<Long> ids);

   
}
