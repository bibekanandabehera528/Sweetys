package com.cart.services.impl;

import com.cart.dtos.CartDto;
import com.cart.dtos.Product;
import com.cart.dtos.User;
import com.cart.entities.AddItemToCartRequest;
import com.cart.entities.Cart;
import com.cart.entities.CartItems;
import com.cart.repositories.CartItemsRepository;
import com.cart.repositories.CartRepository;
import com.cart.services.CartService;
import com.cart.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;



@Service
public class CartServiceImpl implements CartService {

    private final RestTemplate restTemplate;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    private final CartItemsRepository cartItemsRepository;
    @Autowired
    public CartServiceImpl(RestTemplate restTemplate, CartRepository cartRepository, ModelMapper modelMapper, CartItemsRepository cartItemsRepository) {
        this.restTemplate = restTemplate;
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
        this.cartItemsRepository = cartItemsRepository;
    }

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        boolean cartItemUpdated = false;

        Optional<User> user = Optional.ofNullable(restTemplate.getForObject("http://localhost:8081/user/getUserById/" + userId, User.class));
        User user1 = user.orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Optional<Product> product = Optional.ofNullable(restTemplate.getForObject("http://localhost:8083/product/getProductById/" + request.getProductId(), Product.class));
        Product product1 = product.orElseThrow(() -> new ResourceNotFoundException("Product not found !!!"));
        Optional<Cart> cart = Optional.ofNullable(cartRepository.getCartByUserId(userId));
        if (!cart.isPresent()) {
            Cart newCart = new Cart();
            newCart.setCartId(UUID.randomUUID().toString());
            newCart.setCreatedAt(LocalDate.now());
            newCart.setUserId(userId);
            newCart.setCartItems(new ArrayList<>());
            cartRepository.save(newCart);
        }

        Cart finalcart = cartRepository.getCartByUserId(userId);

        List<CartItems> cartItems = finalcart.getCartItems();
        for (CartItems cartItems1 : cartItems) {
            if (cartItems1.getProductId().equals(request.getProductId())) {
                cartItems1.setQuantities(request.getQuantity());
                cartItems1.setTotalPrice(request.getQuantity() * product1.getPrice());
                cartItems1.setCart(finalcart);
                cartItemsRepository.save(cartItems1);
                cartItemUpdated = true;
                break;
            }
        }
        if (!cartItemUpdated) {
            CartItems newCartItem = new CartItems();
            newCartItem.setProductId(request.getProductId());
            newCartItem.setQuantities(request.getQuantity());
            newCartItem.setTotalPrice(request.getQuantity() * product1.getPrice());
            finalcart.getCartItems().add(newCartItem);
            newCartItem.setCart(finalcart);
            cartItemsRepository.save(newCartItem);
        }
        return modelMapper.map(cartRepository.save(finalcart), CartDto.class);
    }

    @Override
    public void removeItemFromCart(int cartItemId) {
        CartItems cartItems = cartItemsRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("Cart item not found !!!"));
        cartItemsRepository.delete(cartItems);
    }

    @Override
    @Transactional
    public void clearCart(String userId) {
        Optional<User> user = Optional.ofNullable(restTemplate.getForObject("http://localhost:8081/user/getUserById/" + userId, User.class));
        User user1 = user.orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Optional<Cart> cart = Optional.ofNullable(cartRepository.getCartByUserId(userId));
        Cart cart1 = cart.orElseThrow(() -> new ResourceNotFoundException("Cart for the user not created yet..."));
        cart1.getCartItems().clear();
        cartRepository.save(cart1);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        Optional<User> user = Optional.ofNullable(restTemplate.getForObject("http://localhost:8081/user/getUserById/" + userId, User.class));
        User user1 = user.orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Optional<Cart> cart = Optional.ofNullable(cartRepository.getCartByUserId(userId));
        Cart cart1 = cart.orElseThrow(() -> new ResourceNotFoundException("Cart for the user not created yet..."));
        return modelMapper.map(cart1, CartDto.class);
    }
}

