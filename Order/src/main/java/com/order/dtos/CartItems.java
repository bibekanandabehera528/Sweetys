package com.order.dtos;

import lombok.Data;

@Data
public class CartItems {
    private int cartItemsId;
    private String productId;
    private int quantities;
    private int totalPrice;

    private Cart cart;
}
