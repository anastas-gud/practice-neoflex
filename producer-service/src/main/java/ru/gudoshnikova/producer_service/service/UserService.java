package ru.gudoshnikova.producer_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gudoshnikova.producer_service.exception.UserNotFoundException;
import ru.gudoshnikova.producer_service.model.User;
import ru.gudoshnikova.producer_service.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<User> findAll(){
        return userRepository.findAll();
    }
    public Optional<User> findById(int id){
        return userRepository.findById(id);
    }
    public User save(User user){
        return userRepository.save(user);
    }
    public User update(int id, User userDetails){
        Optional<User> optionalUser = findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            return save(user);
        }
        throw new UserNotFoundException(id);
    }
    public void delete(User user){
        userRepository.delete(user);
    }
    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
}
