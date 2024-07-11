package com.pdev.ecdemo.services;

import com.pdev.ecdemo.entities.Client;
import com.pdev.ecdemo.entities.Order;
import com.pdev.ecdemo.entities.Product;
import com.pdev.ecdemo.repositories.ClientRepository;
import com.pdev.ecdemo.repositories.OrderRepository;
import com.pdev.ecdemo.repositories.ProductRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class OrderService extends _GenericService<Order> {

    private final String found = "Order found";
    private final String notFound = "Order not found";

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public OrderService(OrderRepository repository, ProductRepository productRepository, OrderRepository orderRepository, ClientRepository clientRepository) {
        super(repository);
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
    }



    /*public List<Order> findAll() {
        return orderRepository.findAll();
    }*/


    /*public Optional<Order> findById(Long id) {

        Optional<Order> order = orderRepository.findById(id);

        if (order.isPresent()) {
            System.out.println(found);

            return order;

        } else {

            System.out.println(notFound);

            return Optional.empty();
        }
    }*/


    public Optional<Order> findByClient (Long clientId) {

        Optional<Client> clientCheck = clientRepository.findById(clientId);
        if (!clientCheck.isPresent()) {
            System.out.println(notFound);
            return Optional.empty();
        }

        Optional<Order> orderCheck = orderRepository.findByClientId(clientCheck.get());
        if (!orderCheck.isPresent()) {
            System.out.println(notFound);

            return Optional.empty();

        } else {

            System.out.println(found);

            return orderCheck;
        }

    }


    /*public Optional<Order> deleteOrder(Long id) {
        Optional<Order> orderCheck = orderRepository.findById(id);
        if (orderCheck.isPresent()) {
            orderRepository.deleteById(id);
            System.out.println("Order deleted");
            return orderCheck;

        } else {

            System.out.println(notFound);

            return Optional.empty();
        }
    }*/


    public Optional<Order.OrderEntry> deleteFromOrder(Long productId, @NotNull Integer amount, Long clientId) {

        Optional<Client> clientCheck = clientRepository.findById(clientId);
        if (!clientCheck.isPresent()) {
            System.out.println(notFound);
            return Optional.empty();
        }

        Optional<Order> orderCheck = orderRepository.findByClientId(clientCheck.get());
        Optional<Order.OrderEntry> entryCheck = orderCheck.get().getEntryList().stream().filter(e -> e.getProductId() == productId).findFirst();
        Optional<Product> prodCheck = productRepository.findById(productId);

        if(!orderCheck.isPresent() || !orderCheck.get().getOpen()) {
            System.out.println("Order not found or closed.");
            return Optional.empty();
        }
        if(!prodCheck.isPresent()) {
            System.out.println("Product not found.");
            return Optional.empty();
        }

        orderCheck.get().removeOrderEntry(entryCheck.get());

        orderRepository.save(orderCheck.get());
        System.out.println("Deleted one entry product from order list.");

        return Optional.empty();
    }


    public Optional<Order.OrderEntry> addToOrder(Long productId, int amount, Long clientId) {

        Optional<Product> prodCheck = productRepository.findById(productId);

        if (!prodCheck.isPresent()) {

            System.out.println("Product not found.");

        } else {

            Integer onStockProduct = prodCheck.get().getStock();

            if (amount <= onStockProduct){
                Order.OrderEntry entry = new Order.OrderEntry(prodCheck.get().getId(), amount, clientId);

                System.out.println("Product check ok. Adding to current open order..");
                Optional<Client> clientCheck = clientRepository.findById(clientId);
                if (!clientCheck.isPresent()) {
                    System.out.println(notFound);
                    return Optional.empty();
                }

                Optional<Order> orderCheck = orderRepository.findByClientId(clientCheck.get());
                Order currentOrder;

                if(!orderCheck.isPresent() || !orderCheck.get().getOpen()) {
                    currentOrder = new Order();

                } else {

                    currentOrder = orderCheck.get();
                }
                currentOrder.addOrderEntry(entry);

                prodCheck.get().setStock(onStockProduct - amount);

                productRepository.save(prodCheck.get());

                orderRepository.save(currentOrder);


                System.out.println("Order saved.");


                return Optional.of(entry);

            } else {

                System.out.println("Not enough stock.");
            }
        }

        return Optional.empty();

    }

    public Optional<Order.OrderEntry> withdrawFromOrder(Long productId, int amount, Long clientId) {

        Optional<Client> clientCheck = clientRepository.findById(clientId);
        if (!clientCheck.isPresent()) {
            System.out.println(notFound);
            return Optional.empty();
        }

        Optional<Order> orderCheck = orderRepository.findByClientId(clientCheck.get());
        Optional<Order.OrderEntry> entryCheck = orderCheck.get().getEntryList().stream().filter(e -> e.getProductId() == productId).findFirst();
        Optional<Product> prodCheck = productRepository.findById(productId);

        if(!prodCheck.isPresent()) {
            System.out.println("Product not found.");

            return Optional.empty();
        }

        if(!orderCheck.isPresent() || !orderCheck.get().getOpen()) {

            System.out.println("Order not found or closed.");

        } else {

            entryCheck.get().setAmount(entryCheck.get().getAmount() - amount);
            orderRepository.save(orderCheck.get());
            System.out.println("Withdrawn" + amount + prodCheck.get().getDescription() +" from order entry.");

            return Optional.of(entryCheck.get());
        }

        return Optional.empty();

    }

}
