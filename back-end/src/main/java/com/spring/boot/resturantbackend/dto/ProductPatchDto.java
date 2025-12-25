package com.spring.boot.resturantbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductPatchDto {
    private Long id;
    private String name;      // optional
    private String image;     // optional
    private String description; // optional
    private Double price;     // optional
    private Long categoryId;  // optional
}
