package com.spring.boot.resturantbackend.controllers;

import com.spring.boot.resturantbackend.dto.CategoryDto;
import com.spring.boot.resturantbackend.dto.ExceptionDto;
import com.spring.boot.resturantbackend.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.SystemException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

 
    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getAllCategories() throws SystemException {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    
    @PostMapping()
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) throws SystemException {
        return ResponseEntity.created(URI.create("create-category")).body(categoryService.createCategory(categoryDto));
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) throws SystemException {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
}
