package com.product.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductDto {
    private String productId;
    @NotEmpty
    @Size(min = 5,max = 30)
    private String title;
    @Size(min = 0,max = 500)
    private String description;
    private LocalDate date;
    private int quantity;
    private int price;
    private int discountedPrice;
    private boolean isLive;
    private boolean stock;
    private String productImage;
}
