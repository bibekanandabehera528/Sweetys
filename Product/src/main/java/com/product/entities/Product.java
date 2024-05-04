package com.product.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    private String productId;
    @Column(length = 50)
    private String title;
    @Column(length = 500)
    private String description;
    private LocalDate date;
    private int quantity;
    private int price;
    private int discountedPrice;
    private boolean isLive;
    private boolean stock;
    private String productImage;

    @Column(name = "category_id")
    private String categoryId;
}
