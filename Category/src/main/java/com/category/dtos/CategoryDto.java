package com.category.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryDto {
    private String categoryId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    private String categoryImage;
}
