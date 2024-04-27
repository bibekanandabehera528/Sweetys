package com.category.services.impl;

import com.category.dtos.CategoryDto;
import com.category.entities.Category;
import com.category.exceptions.ResourceNotFoundException;
import com.category.repositories.CategoryRepository;
import com.category.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    @Value("${category.image.path}")
    private String categoryImagePath;

    @Autowired
    public CategoryServiceImpl(ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        category.setCategoryId(UUID.randomUUID().toString());
        return modelMapper.map(categoryRepository.save(category), CategoryDto.class);
    }

    @Override
    public void removeCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found !!!"));
        File file = new File(categoryImagePath+category.getCategoryImage());
        file.delete();
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDto> getAllCategories(int pageSize, int pageNumber, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return categoryRepository.findAll(pageable).stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(String categoryId, CategoryDto categoryDto) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found !!!"));
        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());
        Category category1 = categoryRepository.save(category);
        return modelMapper.map(category1, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found !!!"));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public FileInputStream getCategoryImage(String categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found...!"));
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(categoryImagePath + category.getCategoryImage()));
        } catch (FileNotFoundException fileNotFoundException) {
        }
        return inputStream;
    }

    @Override
    public void uploadCategoryImage(String categoryId,MultipartFile categoryImage) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found...!"));

        File file = new File(categoryImagePath);
        if(!file.exists()){
            file.mkdir();
        }
        String categoryImageName = UUID.randomUUID().toString()+categoryImage.getOriginalFilename();
        try {
            categoryImage.transferTo(new File(categoryImagePath + categoryImageName));
            category.setCategoryImage(categoryImageName);
            categoryRepository.save(category);
        }catch(IOException ioException){
            ioException.printStackTrace();
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }
}
