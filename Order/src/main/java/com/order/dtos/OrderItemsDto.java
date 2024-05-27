package com.order.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.order.entities.Order;
import lombok.Data;

@Data
public class OrderItemsDto {
    private int orderItemsId;
    private int quantity;
    private int totalPrice;
    @JsonIgnore
    private Order order;
}
