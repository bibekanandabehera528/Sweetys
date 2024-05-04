package com.product.services;


import com.product.dtos.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);

    ProductDto getProductById(String productId);

    List<ProductDto> getAllProducts(int pageSize, int pageNumber, String sortBy);

    ProductDto updateProduct(String productId, ProductDto productDto);

    void deleteProduct(String productId);

    List<ProductDto> getAllLiveProducts();

    List<ProductDto> searchProductByTitle(String title);

    InputStream getProductImage(String productId);

    void uploadProductImage(String productId,MultipartFile productImage);

    ProductDto createProductWithCategory(ProductDto productDto, String categoryId);

    List<ProductDto> getProductsWithCategoryId(String categoryId);
}
