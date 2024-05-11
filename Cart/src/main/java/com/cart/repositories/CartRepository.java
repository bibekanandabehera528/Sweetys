package com.cart.repositories;

import com.cart.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CartRepository extends JpaRepository<Cart, String> {
    @Query(value = "select * from cart where user_id =:userId",nativeQuery = true)
    Cart getCartByUserId(@Param("userId") String userId);
}
