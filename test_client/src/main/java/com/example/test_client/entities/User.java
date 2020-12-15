package com.example.test_client.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class User implements Serializable {
    private UUID carId;
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;

    public User(String firstName, String lastName, String email, String password, Role role, UUID carId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.carId = carId;
    }

    public User(String firstName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
