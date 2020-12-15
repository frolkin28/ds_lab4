package com.example.user_service;

import com.example.user_service.filters.AuthFilter;
import com.example.user_service.grpc.HelloServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(UserServiceApplication.class, args);
		Server server = ServerBuilder.forPort(9000)
				.addService(new HelloServiceImpl()).build();

		System.out.println("Starting server...");
		server.start();
		System.out.println("Server started!");
		server.awaitTermination();
	}

	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		AuthFilter authFilter = new AuthFilter();
		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns("/api/v1/*");
		return registrationBean;
	}

}
