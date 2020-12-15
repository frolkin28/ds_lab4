package com.example.order_service.controllers;

import com.example.order_service.entities.OrderResponse;
import com.example.order_service.entities.User;
import com.example.order_service.models.Order;
import com.example.order_service.services.LocationService;
import com.example.order_service.services.OrderService;
import com.example.order_service.services.UserService;
import com.example.order_service.services.enums.Role;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    private OrderService orderService;
    private UserService userService;
    private LocationService locationService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, LocationService locationService) {
        this.orderService = orderService;
        this.userService = userService;
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<OrderResponse> getOrder(HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        int role = (Integer) request.getAttribute("role");

        if (role == Role.DRIVER.ordinal() && email != null) {
            User driver = userService.getByEmail(email);
            OrderResponse order = orderService.getOrder(driver);
            return new ResponseEntity<>(order, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order, HttpServletRequest request) {
        String email = (String) request.getAttribute("email");

        if (email != null) {
            User user = userService.getByEmail(email);
            Order createdOrder = orderService.createOrder(order, user.getId());
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

}
