package com.example.user_service.controllers;

import com.example.user_service.exceptions.InvalidEmail;
import com.example.user_service.models.User;
import com.example.user_service.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<HashMap<String, String>> register(@RequestBody User user) {
        HashMap<String, String> response = new HashMap<>();
        try {
            registrationService.registerUser(user);
            response.put("message", "User has been successfully registered");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (InvalidEmail e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        catch (DataIntegrityViolationException e) {
            response.put("message", "User already exists");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
