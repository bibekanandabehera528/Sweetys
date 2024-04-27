package com.category.controllers;

import com.category.dtos.CategoryDto;
import com.category.services.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping("/addCategory")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody@Valid CategoryDto categoryDto){
        logger.info("category added with id: " + categoryDto.getCategoryId());
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/getCategoryById/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId){
        logger.info("Getting category with id: " + categoryId);
        return new ResponseEntity<>(categoryService.getCategoryById(categoryId),HttpStatus.OK);
    }

    @GetMapping("/gettingAllCategories")
    public ResponseEntity<List<CategoryDto>> gettingAllCategories(@RequestParam(value = "pageSize",defaultValue = "3",required = false) int pageSize,
                                                                  @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
                                                                  @RequestParam(value = "sortBy",defaultValue = "categoryId",required = false) String sortBy){
        logger.info("Getting all categories...");
        return new ResponseEntity<>(categoryService.getAllCategories(pageSize,pageNumber,sortBy),HttpStatus.OK);
    }

    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryId, @RequestBody@Valid CategoryDto categoryDto){
        logger.info("Updated category with id: " + categoryId);
        return new ResponseEntity<>(categoryService.updateCategory(categoryId,categoryDto),HttpStatus.CREATED);
    }

    @GetMapping("/getCategoryImage/{categoryId}")
    public ResponseEntity<?> getCategoryImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(categoryService.getCategoryImage(categoryId),response.getOutputStream());
        logger.info("Getting category image of category: " + categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addCategoryImage/{categoryId}")
    public ResponseEntity<String> addCategoryImage(@PathVariable String categoryId, MultipartFile categoryImage){
        categoryService.uploadCategoryImage(categoryId,categoryImage);
        logger.info("Category image uploaded for category: "+ categoryId);
        return new ResponseEntity<>("Category Image uploaded...",HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<String> deleteCategroy(@PathVariable String categoryId){
        categoryService.removeCategory(categoryId);
        logger.info("Deleted category: " + categoryId);
        return new ResponseEntity<>("Category deleted...", HttpStatus.OK);
    }
}
