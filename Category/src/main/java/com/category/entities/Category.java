package com.category.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

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
}
