package com.product.services.impl;

import com.product.dtos.ProductDto;
import com.product.entities.Product;
import com.product.exceptions.ResourceNotFoundException;
import com.product.repositories.ProductRepository;
import com.product.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Value("${product.image.path}")
    private String productImagePath;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        productDto.setProductId(UUID.randomUUID().toString());
        productDto.setDate(LocalDate.now());

        Product product = modelMapper.map(productDto, Product.class);
        Product product1 = productRepository.save(product);
        return modelMapper.map(product1, ProductDto.class);
    }

    @Override
    public ProductDto getProductById(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found !!!"));
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public List<ProductDto> getAllProducts(int pageSize, int pageNumber, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        return productRepository.findAll(pageable).stream().map(i -> modelMapper.map(i, ProductDto.class)).collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(String productId, ProductDto productDto) {
        productDto.setDate(LocalDate.now());
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found !!!"));
        product.setTitle(productDto.getTitle());
        product.setStock(productDto.isStock());
        product.setQuantity(productDto.getQuantity());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setPrice(product.getPrice());
        product.setDescription(product.getDescription());
        product.setLive(productDto.isLive());
        product.setDate(productDto.getDate());
        Product product1 = productRepository.save(product);
        return modelMapper.map(product1, ProductDto.class);
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found !!!"));
        File file = new File(productImagePath + product.getProductImage());
        if (file.exists()) {
            file.delete();
        }
        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> getAllLiveProducts() {
        List<Product> products = productRepository.getAllLiveProducts();
        return products.stream().map(i -> modelMapper.map(i, ProductDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchProductByTitle(String title) {
        List<Product> products = productRepository.getAllProductsByTitle(title);
        return products.stream().map(i -> modelMapper.map(i, ProductDto.class)).collect(Collectors.toList());
    }

    @Override
    public InputStream getProductImage(String productId) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found !!!"));
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(productImagePath + product.getProductImage()));
        } catch (FileNotFoundException fileNotFoundException) {
        }
        return inputStream;
    }

    @Override
    public void uploadProductImage(String productId, MultipartFile productImage) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found !!!"));
        String productImageName = UUID.randomUUID().toString() + productImage.getOriginalFilename();
        File file = new File(productImagePath);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            productImage.transferTo(new File(productImagePath + productImageName));
            product.setProductImage(productImageName);
            productRepository.save(product);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
