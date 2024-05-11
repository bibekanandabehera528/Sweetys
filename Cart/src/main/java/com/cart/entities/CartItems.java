package com.cart.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemsId;
    private String productId;
    private int quantities;
    private int totalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
