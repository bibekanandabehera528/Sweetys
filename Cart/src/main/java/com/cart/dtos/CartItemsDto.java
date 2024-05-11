package com.cart.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CartItemsDto {
    private int cartItemsId;
    private String productId;
    private int quantities;
    private int totalPrice;
    @JsonIgnore
    private CartDto cart;
}
