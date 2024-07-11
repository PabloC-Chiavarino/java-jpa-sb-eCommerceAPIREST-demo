package com.pdev.ecdemo.repositories;

import com.pdev.ecdemo.entities.Client;
import com.pdev.ecdemo.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByClientId(Client clientId);
}
