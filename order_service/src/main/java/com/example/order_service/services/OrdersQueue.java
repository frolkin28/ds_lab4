package com.example.order_service.services;

import com.example.order_service.models.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;

@Service
public final class OrdersQueue {
    private final static ArrayDeque<Order> orders_queue = new ArrayDeque<>();

    public void push(Order order) {
        orders_queue.addLast(order);
    }

    public Order pop() {
        return orders_queue.getFirst();
    }
}