package com.example.user_service.services;

import com.example.user_service.exceptions.InvalidEmail;
import com.example.user_service.models.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RegistrationService {
    private final ValidationService validationService;
    private final UserService userService;

    @Autowired
    public RegistrationService(ValidationService validationService, UserService userService) {
        this.validationService = validationService;
        this.userService = userService;
    }

    public void registerUser(User user) throws InvalidEmail {
        validationService.validateEmail(user.getEmail());
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        user.setPassword(hashedPassword);
        userService.add(user);
    }

}

