package ru.gudoshnikova.producer_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "status", nullable = false)
    private String status;
}