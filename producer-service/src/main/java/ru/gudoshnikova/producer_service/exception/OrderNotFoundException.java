package ru.gudoshnikova.producer_service.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(int id) {
        super("Order not found with id: " + id);
    }
}
