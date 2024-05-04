package com.category.dtos;

import com.category.entities.Product;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private String categoryId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    private String categoryImage;

    private List<Product> products;
}
