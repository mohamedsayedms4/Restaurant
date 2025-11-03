package com.spring.boot.restaurant.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChefDto {

    private Long id;

    @NotBlank(message = "Chef name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Specialty is required")
    @Size(min = 3, max = 100, message = "Specialty must be between 3 and 100 characters")
    private String specialty;

    @NotBlank(message = "Logo path is required")
    private String logoPath;

    @NotBlank(message = "Facebook link is required")
    private String faceLink;

    @NotBlank(message = "Twitter link is required")
    private String tweLink;

    @NotBlank(message = "Instagram link is required")
    private String instaLink;
}
