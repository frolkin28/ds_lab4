package com.example.order_service.entities;

import com.example.order_service.services.enums.Role;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String firstName;
    private String lastName;

    private String email;

    private String password;

    private Role role;

    private String carId;
}
