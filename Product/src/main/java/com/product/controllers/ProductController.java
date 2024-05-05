package com.product.controllers;

import com.product.dtos.ProductDto;
import com.product.services.ProductService;
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
@RequestMapping("/product/")
public class ProductController {
    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/createProduct")
    public ResponseEntity<ProductDto> createProduct(@RequestBody@Valid ProductDto productDto) {
        logger.info("Product created: " + productDto.getProductId());
        return new ResponseEntity<>(productService.createProduct(productDto), HttpStatus.CREATED);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(name = "pageSize",defaultValue = "3") int pageSize, @RequestParam(name = "pageNumber",defaultValue = "0") int pageNumber, @RequestParam(name = "sortBy",defaultValue = "productId") String sortBy) {
        logger.info("Getting all products...");
        return new ResponseEntity<>(productService.getAllProducts(pageSize,pageNumber,sortBy), HttpStatus.OK);
    }

    @GetMapping("/getProductById/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String productId) {
        logger.info("Getting product: " + productId);
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    @GetMapping("/getAllLiveProducts")
    public ResponseEntity<List<ProductDto>> getAllLiveProducts() {
        logger.info("Getting all live products...");
        return new ResponseEntity<>(productService.getAllLiveProducts(), HttpStatus.OK);
    }

    @GetMapping("/getAllProductsByTitle/{title}")
    public ResponseEntity<List<ProductDto>> getAllProductsByTitle(@PathVariable String title) {
        logger.info("Getting all products by title...");
        return new ResponseEntity<>(productService.searchProductByTitle(title), HttpStatus.OK);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
        logger.info("Deleted product: "+ productId);
        return new ResponseEntity<>("Product deleted !!!", HttpStatus.OK);
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId, @RequestBody@Valid ProductDto productDto) {
        logger.info("Updated product: " + productId);
        return new ResponseEntity<>(productService.updateProduct(productId, productDto), HttpStatus.OK);
    }

    @GetMapping("/getProductImage/{productId}")
    public ResponseEntity<?> viewProductImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(productService.getProductImage(productId),response.getOutputStream());
        logger.info("Loading product image for: " + productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/uploadProductImage/{productId}")
    public ResponseEntity<?> uploadProductImage(@PathVariable String productId, MultipartFile productImage){
        productService.uploadProductImage(productId,productImage);
        logger.info("Uploading product image to: " + productId);
        return new ResponseEntity<>("Product image uploaded...", HttpStatus.OK);
    }

    @PostMapping("/createProductWithCategory/{categoryId}")
    public ResponseEntity<ProductDto> createProductWithCategory(@PathVariable String categoryId, @RequestBody@Valid ProductDto productDto) {
        logger.info("Product created: " + productDto.getProductId() + " for category: "+ categoryId);
        return new ResponseEntity<>(productService.createProductWithCategory(productDto), HttpStatus.CREATED);
    }

    @GetMapping("/getProductsWithCategoryId/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsWithCategoryId(@PathVariable String categoryId){
        logger.info("Getting all products with category id: " + categoryId);
        return new ResponseEntity<>(productService.getProductsWithCategoryId(categoryId),HttpStatus.OK);
    }
}
