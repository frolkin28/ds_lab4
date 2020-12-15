package com.example.user_service.controllers;

import com.example.user_service.models.User;
import com.example.user_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("api/internal/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("email/{email}")
    public ResponseEntity<User> getByEmail(@PathVariable(value = "email") String email ) {
        User user = userService.getByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<User> getById(@PathVariable(value = "id") UUID id ) {
        User user = userService.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") UUID id ) {
        userService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
