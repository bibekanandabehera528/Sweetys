package com.cart.services;

import com.cart.dtos.CartDto;
import com.cart.entities.AddItemToCartRequest;

public interface CartService {
    CartDto addItemToCart(String userId,AddItemToCartRequest request);

    void removeItemFromCart(int cartItemId );

    void clearCart(String userId);

    CartDto getCartByUser(String userId);
}
