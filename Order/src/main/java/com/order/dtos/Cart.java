package com.order.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private String cartId;
    private String userId;
    private LocalDate createdAt;
    private List<CartItems> cartItems = new ArrayList<>();
    private int totalAmount;
}