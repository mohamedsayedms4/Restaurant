package com.spring.boot.resturantbackend.controllers;

import com.spring.boot.resturantbackend.controllers.vm.ProductResponseVm;
import com.spring.boot.resturantbackend.dto.ExceptionDto;
import com.spring.boot.resturantbackend.dto.ProductDto;
import com.spring.boot.resturantbackend.dto.ProductPatchDto;
import com.spring.boot.resturantbackend.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.SystemException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(
		name="Product",
		description = "Products APIs"
)
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Operation(
    		summary="Get API for product",
    		description = "Get All Products"
    		)
    @ApiResponses({
    	@ApiResponse(
    			responseCode = "200",
    			description = "HTTP Status ok"
    			),
    	@ApiResponse(
    			responseCode = "500",
    			description = "HTTP RunTimeException",
    			content = @Content(
    					schema = @Schema(implementation = ExceptionDto.class)
    					)
    			)
    })
    @GetMapping("")
    public ResponseEntity<ProductResponseVm> getAllProducts(@RequestParam int page, @RequestParam int size)
            throws SystemException {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseVm> getAllProductsByCategoryId(@PathVariable Long id, @RequestParam int page, @RequestParam int size)
            throws SystemException {
        return ResponseEntity.ok(productService.getAllProductsByCategoryId(id, page, size));
    }


    @GetMapping("/search-all-products-by-key")
    public ResponseEntity<ProductResponseVm> getAllProductsByKey(@RequestParam String key, @RequestParam int page, @RequestParam int size)
            throws SystemException {
        return ResponseEntity.ok(productService.getAllProductsByKey(key, page, size));
    }

    
    @GetMapping("/all-products-by-key-and-category-id")
    public ResponseEntity<ProductResponseVm> getAllProductsByKeyAndCategoryId(
            @RequestParam Long categoryId,
            @RequestParam String key,
            @RequestParam int page,
            @RequestParam int size
    )
            throws SystemException {
        return ResponseEntity.ok(productService.getAllProductsByCategoryIdAndKey(categoryId, key, page, size));
    }
    
    
    // add new product
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")  
    public ResponseEntity<ProductDto> addproduct(@RequestBody @Valid ProductDto productDto)
    {
    	ProductDto created = productService.createProduct(productDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }
    
    // Delete By id  
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok("Product deleted successfully with ID: " + id);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error deleting product: " + e.getMessage());
        }
    }

//    update producct
    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> patchProduct(
            @PathVariable Long id,
            @RequestBody ProductPatchDto dto) throws SystemException {

        dto.setId(id); // enforce path id
        return ResponseEntity.ok(productService.updateProduct(dto));
    }


}
