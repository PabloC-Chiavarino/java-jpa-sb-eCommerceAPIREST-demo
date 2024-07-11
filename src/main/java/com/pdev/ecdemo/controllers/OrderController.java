package com.pdev.ecdemo.controllers;


import com.pdev.ecdemo.entities.Order;
import com.pdev.ecdemo.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController @RequestMapping(path = "/api/v1/orders")
public class OrderController extends _GenericController<Order> {

    OrderService orderService;

    @Autowired
    public OrderController(OrderService service) {
        super(service);
        this.orderService = service;
    }


    /*@GetMapping(path = "/all")
    public ResponseEntity<List<Order>> findAll() {
        try {
            List<Order> orders = orderService.findAll();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }*/

    /*@GetMapping(path = "find/{id}")
    public ResponseEntity<Order> findOne(@Valid @PathVariable Long id) {
        try {
            Optional<Order> order = orderService.findById(id);
            if (order.isPresent()) {
                return ResponseEntity.ok(order.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }*/

    @GetMapping(path = "/client/{id}")
    public ResponseEntity<Order> findByClientId(@Valid @PathVariable Long id) {
        try {
            Optional<Order> order = orderService.findByClient(id);
            if (order.isPresent()) {
                return ResponseEntity.ok(order.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /*@DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long id) {
        try {
            Optional<Order> order = orderService.deleteOrder(id);
            if (order.isPresent()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }*/

    @DeleteMapping(path = "/delete/entry")
    public ResponseEntity<Order.OrderEntry> deleteEntry(@RequestBody @Valid Order.OrderEntry data, @PathVariable Long clientId) {
        try {
            Optional<Order.OrderEntry> order = orderService.deleteFromOrder(data.getProductId(), data.getAmount(), data.getClientId());
            if (order.isPresent()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping(path = "/add_to_order/{clientId}")
    public ResponseEntity<Order.OrderEntry> addToOrder(@RequestBody @Valid Order.OrderEntry data, @PathVariable Long clientId) {
        try {
            Optional<Order.OrderEntry> order = orderService.addToOrder(data.getProductId(), data.getAmount(), clientId);
            if (order.isPresent()) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(order.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(path = "/withdraw_from_order/{client_id}")
    public ResponseEntity<Order.OrderEntry> withdrawFromOrder(@RequestBody @Valid Order.OrderEntry data, @PathVariable Long clientId) {
        try {
            Optional<Order.OrderEntry> order = orderService.withdrawFromOrder(data.getProductId(), data.getAmount(), data.getClientId());
            if (order.isPresent()) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(order.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
