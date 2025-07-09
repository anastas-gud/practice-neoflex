package ru.gudoshnikova.producer_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gudoshnikova.producer_service.exception.UserNotFoundException;
import ru.gudoshnikova.producer_service.exception.UserValidationException;
import ru.gudoshnikova.producer_service.model.User;
import ru.gudoshnikova.producer_service.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve users", e);
        }
    }

    public User findById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User save(User user) {
        validateUser(user);
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }

    public User update(int id, User userDetails) {
        validateUser(userDetails);
        User user = findById(id);
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user", e);
        }
    }

    public void deleteById(int id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    private void validateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            throw new UserValidationException("User name cannot be empty");
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new UserValidationException("Invalid email format");
        }
    }
}
