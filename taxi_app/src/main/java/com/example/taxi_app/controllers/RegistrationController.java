package com.example.taxi_app.controllers;

import com.example.grpc.RegistrationResponse;
import com.example.taxi_app.entities.User;
import com.example.taxi_app.services.FlagService;
import com.example.taxi_app.services.RegistrationGrpcService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/register")
public class RegistrationController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String userDomain = System.getenv("USER_SERVICE_HOST");
    private final String userPort = System.getenv("HTTP_PORT");

    @PostMapping
    public ResponseEntity<String> register(@RequestBody User userMap) {
        FlagService flags = new FlagService();
        if(flags.getREST_Flag()) {
            System.out.println(flags.getREST_Flag());
            String url = String.format("http://%s:%s/register", this.userDomain, this.userPort);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<User> entity = new HttpEntity<>(userMap, headers);
            ResponseEntity<String> response = this.restTemplate.postForEntity(url, entity, String.class);
            return response;
        }
        else if (flags.getGRPC_Flag()) {
            RegistrationGrpcService grpcService = new RegistrationGrpcService();
            RegistrationResponse registrationResponse = grpcService.register(userMap);
            System.out.println(registrationResponse);
            return new ResponseEntity<>(registrationResponse.toString(),  HttpStatus.CREATED);
        }
        return null;
    }
}
