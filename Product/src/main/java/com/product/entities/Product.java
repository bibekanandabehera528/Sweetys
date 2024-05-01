package com.product.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
}
