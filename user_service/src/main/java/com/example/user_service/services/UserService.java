package com.example.user_service.services;

import com.example.user_service.models.User;
import com.example.user_service.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getById(UUID id) {
        return userRepository.findById(id);
    }

    public void add(User driver) {
        userRepository.save(driver);
    }

    public void remove(UUID id) {
        userRepository.deleteById(id);
    }

    public User validate(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword()))
            return user;
        else
            return null;
    }
}
