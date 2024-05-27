package com.order.services.impl;

import com.order.dtos.Cart;
import com.order.dtos.OrderDto;
import com.order.dtos.User;
import com.order.entities.Order;
import com.order.entities.OrderItems;
import com.order.exceptions.ResourceNotFoundException;
import com.order.repositories.OrderItemsRepository;
import com.order.repositories.OrderRepository;
import com.order.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    public OrderServiceImpl(ModelMapper modelMapper, OrderRepository orderRepository, RestTemplate restTemplate, OrderItemsRepository orderItemsRepository){
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
        this.orderItemsRepository = orderItemsRepository;
    }

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final OrderItemsRepository orderItemsRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Optional<User> user = Optional.ofNullable(restTemplate.getForObject("http://USER-SERVICE/user/getUserById/"+orderDto.getUserId(),User.class));
        User user1 = user.orElseThrow(() -> new ResourceNotFoundException("User doesn't exist"));

        Optional<Cart> cart = Optional.ofNullable(restTemplate.getForObject("http://CART-SERVICE/cart/getCartByUser/"+user1.getUserId(),Cart.class));
        Cart cart1 = cart.orElseThrow(() -> new ResourceNotFoundException("Cart not found !!!"));

        Order order = modelMapper.map(orderDto, Order.class);
        List<OrderItems> orderItems = new ArrayList<>();
        orderItems.addAll(
        cart1.getCartItems().stream().map(
          i -> {
              OrderItems orderItems1 = new OrderItems();
              orderItems1.setProductId(i.getProductId());
              orderItems1.setQuantity(i.getQuantities());
              orderItems1.setTotalPrice(i.getTotalPrice());
              orderItems1.setOrder(order);
              return orderItems1;
          }
        ).collect(Collectors.toList()));
        order.setOrderItems(orderItems);
        order.setOrderAmount(cart1.getTotalAmount());
        order.setOrderItems(orderItems);
        //clearing cart
        restTemplate.delete("http://CART-SERVICE/cart/clearCart/"+cart1.getUserId());
        order.setOrderId(UUID.randomUUID().toString());
        return modelMapper.map(orderRepository.save(order), OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order doesn't exist !!!"));
        orderRepository.delete(order);
    }

    @Override
    public List<OrderDto> getOrdersByUser(String userId) {
        List<Order> orders = orderRepository.getOrdersByUser(userId);
        return orders.stream().map(i -> modelMapper.map(i, OrderDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllOrders(int pageNum, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNum,pageSize, Sort.by(sortBy).ascending());
        List<Order> orders = orderRepository.findAll(pageable).getContent();
        return orders.stream().map(i -> modelMapper.map(i, OrderDto.class)).collect(Collectors.toList());
    }
}
