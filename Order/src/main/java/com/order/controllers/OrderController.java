package com.order.controllers;

import com.order.dtos.OrderDto;
import com.order.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    private final OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("/createOrder")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto){
        logger.info("Generating order: "+ orderDto.getOrderId());
        return new ResponseEntity<>(orderService.createOrder(orderDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/removeOrder/{orderId}")
    public ResponseEntity<?> removeOrder(@PathVariable String orderId){
        orderService.removeOrder(orderId);
        logger.info("Order " + orderId + " removed...");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getOrderByUser/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUser(@PathVariable String userId){
        logger.info("Getting all orders of user: " + userId);
        return new ResponseEntity<>(orderService.getOrdersByUser(userId),HttpStatus.OK);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<OrderDto>> getAllOrders(@RequestParam(value = "pageNum",defaultValue = "0",required = false) int pageNum,
                                                       @RequestParam(value = "pageSize",defaultValue = "3",required = false) int pageSize,
                                                       @RequestParam(value = "sortBy",defaultValue = "orderId",required = false) String sortBy){
        logger.info("Getting all orders...");
        return new ResponseEntity<>(orderService.getAllOrders(pageNum,pageSize,sortBy),HttpStatus.OK);
    }
    
}
