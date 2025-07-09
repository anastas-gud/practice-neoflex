package ru.gudoshnikova.producer_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gudoshnikova.producer_service.exception.OrderNotFoundException;
import ru.gudoshnikova.producer_service.exception.OrderValidationException;
import ru.gudoshnikova.producer_service.exception.UserNotFoundException;
import ru.gudoshnikova.producer_service.model.Order;
import ru.gudoshnikova.producer_service.model.User;
import ru.gudoshnikova.producer_service.repository.OrderRepository;
import ru.gudoshnikova.producer_service.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public List<Order> findAll() {
        try {
            return orderRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve orders", e);
        }
    }

    public Order findById(int id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order save(Order order) {
        validateOrder(order);
        try {
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save order", e);
        }
    }

    public Order update(int id, Order orderDetails) {
        validateOrder(orderDetails);
        Order order = findById(id);
        order.setUserId(orderDetails.getUserId());
        order.setAmount(orderDetails.getAmount());
        order.setStatus(orderDetails.getStatus());
        try {
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update order", e);
        }
    }

    public void deleteById(int id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        try {
            orderRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete order", e);
        }
    }

    private void validateOrder(Order order) {
        if (order.getUserId() == null || !userRepository.existsById(order.getUserId())) {
            throw new UserNotFoundException(order.getUserId());
        }
        if (order.getAmount() == null || order.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new OrderValidationException("Amount must be positive");
        }
        if (order.getStatus() == null || order.getStatus().isBlank()) {
            throw new OrderValidationException("Status cannot be empty");
        }
    }
}
