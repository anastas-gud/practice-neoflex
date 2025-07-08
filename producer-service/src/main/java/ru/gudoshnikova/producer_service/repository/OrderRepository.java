package ru.gudoshnikova.producer_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gudoshnikova.producer_service.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
