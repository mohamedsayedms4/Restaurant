package com.spring.boot.resturantbackend.services.impl;

import com.spring.boot.resturantbackend.controllers.vm.ProductResponseVm;
import com.spring.boot.resturantbackend.dto.ProductDto;
import com.spring.boot.resturantbackend.dto.ProductPatchDto;
import com.spring.boot.resturantbackend.mappers.ProductMapper;
import com.spring.boot.resturantbackend.models.Category;
import com.spring.boot.resturantbackend.models.Product;
import com.spring.boot.resturantbackend.repositories.CategoryRepo;
import com.spring.boot.resturantbackend.repositories.ProductRepo;
import com.spring.boot.resturantbackend.services.CategoryService;
import com.spring.boot.resturantbackend.services.ProductService;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepo categoryRepo;
    
    @Override
    public ProductResponseVm getAllProducts(int page, int size) {
        try {
            Pageable pageable = getPageable(page, size);
            Page<Product> result = productRepo.findAllByOrderByIdAsc(pageable);
            if (result.isEmpty()) {
                throw new SystemException("products.not.found");
            }
            return new ProductResponseVm(
                    result.getContent().stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList(),
                    result.getTotalElements()
            );
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProductResponseVm getAllProductsByCategoryId(Long id, int page, int size) {
        try {
            if (Objects.isNull(id)) {
                throw new SystemException("id.must_be.not_null");
            }
            Pageable pageable = getPageable(page, size);
            Page<Product> result = productRepo.findAllProductsByCategoryIdByOrderByIdAsc(id, pageable);
            if (result.isEmpty()) {
                throw new SystemException("products.category.not.found");
            }
            return new ProductResponseVm(
                    result.getContent().stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList(),
                    result.getTotalElements()
            );
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

//    @Override
//    public ProductDto createProduct(ProductDto productDto) {
//        try {
//            if (Objects.nonNull(productDto.getId())) {
//                throw new SystemException("id.must_be.null");
//            }
//            Product product = ProductMapper.PRODUCT_MAPPER.toProduct(productDto);
//            product = productRepo.save(product);
//            return ProductMapper.PRODUCT_MAPPER.toProductDto(product);
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }
    
    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        try {
            // 1-
            if (Objects.nonNull(productDto.getId())) {
                throw new SystemException("id.must_be.null");
            }

            Category category;

            // 2-
            if (productDto.getCategory() != null && productDto.getCategory().getId() != null) {
                category = categoryRepo.findById(productDto.getCategory().getId())
                        .orElseThrow(() -> new SystemException("category.not.found"));
            } else if (productDto.getCategory() != null && productDto.getCategory().getName() != null) {
                category = categoryRepo.findByName(productDto.getCategory().getName())
                        .orElseGet(() -> {
                            Category newCategory = new Category();
                            newCategory.setName(productDto.getCategory().getName());
                            newCategory.setLogo(productDto.getCategory().getLogo());
                            newCategory.setFlag(productDto.getCategory().getFlag());
                            return categoryRepo.save(newCategory);
                        });
            } else {
                throw new SystemException("category.id.or.name.required");
            }

            // 3-
            Product product = ProductMapper.PRODUCT_MAPPER.toProduct(productDto);

            // 4-
            product.setCategory(category);

            // 5-
            product = productRepo.save(product);

            return ProductMapper.PRODUCT_MAPPER.toProductDto(product);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ProductDto> createListOfProduct(List<ProductDto> productDto) {
        try {
            if (productDto.isEmpty()) {
                throw new SystemException("error.empty.list.product");
            }
            List<Product> products = productDto.stream().map(ProductMapper.PRODUCT_MAPPER::toProduct).toList();
            products = productRepo.saveAll(products);
            return products.stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProductDto updateProduct(ProductPatchDto dto) throws SystemException {

        if (dto.getId() == null) {
            throw new SystemException("id.must_be.not_null");
        }

        Product product = productRepo.findById(dto.getId())
                .orElseThrow(() -> new SystemException("product.not.found"));

        // update only provided fields
        if (dto.getName() != null) product.setName(dto.getName());
        if (dto.getImage() != null) product.setImage(dto.getImage());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (dto.getPrice() != null) product.setPrice(dto.getPrice());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepo.findById(dto.getCategoryId())
                    .orElseThrow(() -> new SystemException("category.not.found"));
            product.setCategory(category);
        }

        Product saved = productRepo.save(product);
        return ProductMapper.PRODUCT_MAPPER.toProductDto(saved);
    }

    @Override
    public List<ProductDto> updateListOfProduct(List<ProductDto> productDto) {
        try {
            if (productDto.isEmpty()) {
                throw new SystemException("error.empty.list.category");
            }
            List<Product> products = productDto.stream().map(ProductMapper.PRODUCT_MAPPER::toProduct).toList();
            products = productRepo.saveAll(products);
            return products.stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteProductById(Long id) {
        try {
            if (Objects.isNull(id)) {
                throw new SystemException("id.must_be.not_null");
            }
            getProductById(id);
            productRepo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void deleteListOfProduct(List<Long> productIds) {
        try {
            if (productIds.isEmpty()) {
                throw new SystemException("error.empty.list.category");
            }
            productRepo.deleteAllById(productIds);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProductDto getProductById(Long id) {
        try {
            if (Objects.isNull(id)) {
                throw new SystemException("id.must_be.not_null");
            }
            Optional<Product> result = productRepo.findById(id);
            if (result.isEmpty()) {
                throw new SystemException("products.not.found");
            }
            return ProductMapper.PRODUCT_MAPPER.toProductDto(result.get());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProductResponseVm getAllProductsByKey(String key, int page, int size) {
        try {
            if (Objects.isNull(key)) {
                throw new SystemException("key.null");
            }
            Pageable pageable = getPageable(page, size);
            Page<Product> result = productRepo.getAllProductsByKeyByOrderByIdAsc(key, pageable);
            if (result.isEmpty()) {
                throw new SystemException("products.not.found");
            }
            return new ProductResponseVm(
                    result.getContent().stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList(),
                    result.getTotalElements()
            );
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProductResponseVm getAllProductsByCategoryIdAndKey(Long categoryId, String key, int page, int size) {
        try {
            if (Objects.isNull(key)) {
                throw new SystemException("key.null");
            }
            categoryService.getCategoryById(categoryId);
            Pageable pageable = getPageable(page, size);
            Page<Product> result = productRepo.getAllProductsByKeyByCategoryIdByOrderByIdAsc(categoryId, key, pageable);
            if (result.isEmpty()) {
                throw new SystemException("products.not.found");
            }
            return new ProductResponseVm(
                    result.getContent().stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList(),
                    result.getTotalElements()
            );
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static Pageable getPageable(int page, int size) {
        try {
            if (page < 1) {
                throw new SystemException("error.min.one.page");
            }
            return PageRequest.of(page - 1, size);
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

	@Override
	public  List<ProductDto> getProductsByIds(List<Long> productIds) {
		 try {
	            if (productIds.isEmpty()) {
	                throw new SystemException("error.empty.list.products");
	            }
	            
	           return productRepo.findAllById(productIds).stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList();
	           
	        } catch (Exception e) {
	            throw new RuntimeException(e.getMessage());
	        }
		
		
	}
}
