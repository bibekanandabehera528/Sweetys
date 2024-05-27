package com.order.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.order.enums.OrderStatus;
import com.order.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDto {
    private String orderId;
    private OrderStatus orderStatus = OrderStatus.PENDING;
    private PaymentStatus paymentStatus = PaymentStatus.NOT_PAID;
    private int orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate orderedDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate deliveredDate = LocalDate.now();

    private String userId;
//    private String cartId;
    private List<OrderItemsDto> orderItems;
}
