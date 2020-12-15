package com.example.user_service.exceptions;

public class InvalidEmail extends Exception {
    public InvalidEmail(String message) {
        super(message);
    }
}