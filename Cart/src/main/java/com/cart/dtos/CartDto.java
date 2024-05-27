package com.cart.dtos;

import com.cart.entities.CartItems;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CartDto {
    private String cartId;
    private String userId;
    private LocalDate createdAt;
    private List<CartItemsDto> cartItems = new ArrayList<>();
    private int totalAmount;
}
