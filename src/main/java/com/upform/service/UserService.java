package com.upform.service;

import com.upform.exception.UserNotFoundException;
import com.upform.model.User;
import com.upform.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;

    // Constructor injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //Get one user by ID
    public User getUserById(Long id) {
        logger.info("Fetching user with ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    //Get all users
    public List<User> getAllUsers() {
        logger.info("Fetching all users.");
        return userRepository.findAll();
    }
}
