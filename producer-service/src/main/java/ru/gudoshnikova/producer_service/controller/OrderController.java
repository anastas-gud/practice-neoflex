package ru.gudoshnikova.producer_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gudoshnikova.producer_service.model.Order;
import ru.gudoshnikova.producer_service.model.User;
import ru.gudoshnikova.producer_service.service.OrderService;
import ru.gudoshnikova.producer_service.service.UserService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService){
        this.orderService=orderService;
    }
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.save(order);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Integer id) {
        return orderService.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Integer id, @RequestBody Order order) {
        order.setId(id);
        return orderService.update(id, order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Integer id) {
        orderService.deleteById(id);
    }
}
