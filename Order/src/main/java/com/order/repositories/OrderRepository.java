package com.order.repositories;

import com.order.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {
    @Query(value = "select * from orders where user_id = :userId", nativeQuery = true)
    List<Order> getOrdersByUser(@Param("userId") String userId);
}
