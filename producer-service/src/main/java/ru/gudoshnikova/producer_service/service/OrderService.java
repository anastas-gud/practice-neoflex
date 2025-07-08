package ru.gudoshnikova.producer_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gudoshnikova.producer_service.exception.OrderNotFoundException;
import ru.gudoshnikova.producer_service.exception.UserNotFoundException;
import ru.gudoshnikova.producer_service.model.Order;
import ru.gudoshnikova.producer_service.model.User;
import ru.gudoshnikova.producer_service.repository.OrderRepository;
import ru.gudoshnikova.producer_service.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    public List<Order> findAll(){
        return orderRepository.findAll();
    }
    public Optional<Order> findById(int id){
        return orderRepository.findById(id);
    }
    public Order save(Order order){
        return orderRepository.save(order);
    }
    public Order update(int id, Order orderDetails){
        Optional<Order> optionalOrder = findById(id);
        if (userRepository.findById(orderDetails.getUserId()).isEmpty()) throw new UserNotFoundException(orderDetails.getUserId());
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setUserId(orderDetails.getUserId());
            order.setAmount(orderDetails.getAmount());
            order.setStatus(orderDetails.getStatus());
            return save(order);
        }
        throw new OrderNotFoundException(id);
    }
    public void delete(Order order){
        orderRepository.delete(order);
    }
    public boolean existsById(int id) {
        return orderRepository.existsById(id);
    }
    public void deleteById(int id) {
        orderRepository.deleteById(id);
    }
}
