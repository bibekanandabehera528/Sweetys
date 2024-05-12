package com.cart.controllers;

import com.cart.dtos.CartDto;
import com.cart.entities.AddItemToCartRequest;
import com.cart.services.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    public CartController(CartService cartService){
        this.cartService = cartService;
    }
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final CartService cartService;

    @PostMapping("/addItemToCart/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest addItemToCartRequest){
        logger.info("Items to cart added...");
        return new ResponseEntity<>(cartService.addItemToCart(userId,addItemToCartRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/removeItemsFromCart/cartItemId/{cartItemId}")
    public ResponseEntity<?> removeItemsFromCart(@PathVariable int cartItemId){
        cartService.removeItemFromCart(cartItemId);
        logger.info("Cart item successfully removed...");
        return new ResponseEntity<>("Cart item successfully removed...",HttpStatus.OK);
    }

    @DeleteMapping("/clearCart/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable String userId){
        cartService.clearCart(userId);
        logger.info("Cart cleared...");
        return new ResponseEntity<>("cart cleared...",HttpStatus.OK);
    }

    @GetMapping("/getCartByUser/{userId}")
    public ResponseEntity<CartDto> getCartByUser(@PathVariable String userId){
        logger.info("Getting cart for user: " + userId);
        return new ResponseEntity<>(cartService.getCartByUser(userId),HttpStatus.OK);
    }
}
