package com.example.user_service.services;

import com.example.user_service.exceptions.InvalidEmail;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationService {
    private static final String email_pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern compiled_email_pattern = Pattern.compile(email_pattern);

    public void validateEmail(String email) throws InvalidEmail {
        Matcher matcher = compiled_email_pattern.matcher(email);
        boolean found = matcher.matches();
        if (!found)
            throw new InvalidEmail("Wrong email");
    }
}