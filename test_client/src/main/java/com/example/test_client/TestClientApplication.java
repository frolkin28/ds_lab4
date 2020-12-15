package com.example.test_client;

import com.example.test_client.entities.Car;
import com.example.test_client.entities.Location;
import com.example.test_client.entities.Order;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.test_client.services.UserTestService;
import com.example.test_client.services.DriverTestService;

@SpringBootApplication
public class TestClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestClientApplication.class, args);
		UserTestService userTestService = new UserTestService();
		DriverTestService driverTestService = new DriverTestService();

		userTestService.testUserRegistration();
		userTestService.testLogin();
		Car createdCar = userTestService.testCarCreation();
		Location start = new Location(40.7177f, -73.91766f, "New York");
		start = userTestService.testCreateLocation(start);
		Location destination = new Location(41.159824f, -73.217285f, "Bridgeport");
		destination = userTestService.testCreateLocation(destination);
		Order order = new Order(start.getId().toString(), destination.getId().toString());
		userTestService.testCreateOrder(order);

		driverTestService.testDriverRegistration(createdCar.getId());
		driverTestService.testLoginDriver();
		driverTestService.testGetOrder();
	}

}
