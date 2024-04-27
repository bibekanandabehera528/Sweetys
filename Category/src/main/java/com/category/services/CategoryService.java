package com.category.services;

import com.category.dtos.CategoryDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);

    void removeCategory(String categoryId);

    List<CategoryDto> getAllCategories(int pazeSize, int pageNumber, String sortBy);

    CategoryDto updateCategory(String categoryId, CategoryDto categoryDto);

    CategoryDto getCategoryById(String categoryId);

    FileInputStream getCategoryImage(String categoryId);

    void uploadCategoryImage(String categoryId,MultipartFile categoryImage);
}
