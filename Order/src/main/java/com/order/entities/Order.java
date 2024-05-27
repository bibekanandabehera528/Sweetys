package com.order.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.order.enums.OrderStatus;
import com.order.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Table(name = "orders")
@Entity
@Data
public class Order {
    @Id
    private String orderId;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private int orderAmount;
    @Column(length = 1000)
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private LocalDate orderedDate;
    private LocalDate deliveredDate;

    private String userId;
//    private String cartId;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems;
}
