package com.example.taxi_app.controllers;

import com.example.grpc.LoginResponse;
import com.example.taxi_app.entities.User;
import com.example.taxi_app.services.FlagService;
import com.example.taxi_app.services.LoginGrpcService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("login")
public class LoginController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String userDomain = System.getenv("USER_SERVICE_HOST");
    private final String userPort = System.getenv("HTTP_PORT");

    @PostMapping
    public HttpEntity<Map> login(@RequestBody User userMap) {
        FlagService flags = new FlagService();
        if(flags.getREST_Flag()) {
            String url = String.format("http://%s:%s/login", this.userDomain, this.userPort);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<User> entity = new HttpEntity<>(userMap, headers);
            ResponseEntity<Map> response = this.restTemplate.postForEntity(url, entity, Map.class);
            return response;
        }
        else if (flags.getGRPC_Flag()) {
            LoginGrpcService grpcService = new LoginGrpcService();
            LoginResponse loginResponse = grpcService.login(userMap);
            System.out.println(loginResponse);
            Map<String, Object> map = new HashMap<>();
            map.put("token", loginResponse.getToken());
            return new ResponseEntity<>(map,  HttpStatus.OK);
        }
        return null;
    }
}
