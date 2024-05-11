package com.cart.entities;

import lombok.Data;

@Data
public class AddItemToCartRequest {
    private String productId;
    private int quantity;
}
