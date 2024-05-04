package com.category.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "categories")
@Data
public class Category {
    @Id
    private String categoryId;
    @Column(length = 30)
    private String title;
    @Column(length = 500)
    private String description;
    private String categoryImage;

    @Transient
    private List<Product> products;
}
