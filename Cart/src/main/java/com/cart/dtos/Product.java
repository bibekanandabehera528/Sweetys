package com.cart.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Product {
    private String productId;
    private String categoryId;
    private String title;
    private String description;
    private LocalDate date;
    private int quantity;
    private int price;
    private int discountedPrice;
    private boolean isLive;
    private boolean stock;
    private String productImage;
}
