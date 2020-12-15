package com.example.order_service.services;

import com.example.order_service.entities.Car;
import com.example.order_service.entities.Location;
import com.example.order_service.entities.OrderResponse;
import com.example.order_service.entities.User;
import com.example.order_service.models.Order;
import com.example.order_service.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class OrderService {
    private OrderRepository orderRepository;
    private CostService costService;
    private OrdersQueue ordersQueue;
    private LocationService locationService;
    private CarService carService;
    private UserService userService;

    @Autowired
    public OrderService(OrderRepository orderRepository, CostService costService, OrdersQueue ordersQueue, LocationService locationService, CarService carService, UserService userService) {
        this.orderRepository = orderRepository;
        this.costService = costService;
        this.ordersQueue = ordersQueue;
        this.locationService = locationService;
        this.carService = carService;
        this.userService = userService;
    }

    @Transactional
    public OrderResponse getOrder(User driver) {
        Order order = ordersQueue.pop();
        order.setDriverId(driver.getId());
        Location start = locationService.getById(order.getStartId());
        Location destination = locationService.getById(order.getDestinationId());
        Car car = carService.getById(driver.getCarId());
        User customer = userService.getById(order.getCustomerId().toString());
        OrderResponse responseResponse = new OrderResponse(start, destination, order, driver, customer, car);
        orderRepository.save(order);
        return responseResponse;
    }

    public Order createOrder(Order order, String userId) {
        Location start = locationService.getById(order.getStartId());
        Location destination = locationService.getById(order.getDestinationId());
        float cost = costService.calculate(start, destination);
        order.setCost(cost);
        order.setCustomerId(userId);
        ordersQueue.push(order);
        return orderRepository.save(order);
    }
}
