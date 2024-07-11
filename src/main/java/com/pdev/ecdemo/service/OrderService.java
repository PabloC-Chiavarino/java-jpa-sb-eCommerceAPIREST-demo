package com.pdev.ecdemo.service;

import com.pdev.ecdemo.dto.OrderDTO;
import com.pdev.ecdemo.entity.Client;
import com.pdev.ecdemo.entity.Order;
import com.pdev.ecdemo.entity.Product;
import com.pdev.ecdemo.mapper.OrderMapper;
import com.pdev.ecdemo.repository.OrderRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Getter
public class OrderService extends _GenericService<Order, OrderDTO> {

    private final String found = "Order found";
    private final String notFound = "Order not found";

    private final _DataWriteService dataWriteService;
    private final _DataProviderService dataProvider;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        _DataWriteService dataWriteService,
                        _DataProviderService dataProvider,
                        OrderMapper orderMapper) {

        super(orderRepository, orderMapper);
        this.dataWriteService = dataWriteService;
        this.dataProvider = dataProvider;
        this.orderMapper = orderMapper;
    }

    public Optional<OrderDTO> findByClient(Long clientId) {

        Optional<Client> client = dataProvider.getClient(clientId);

        if (!client.isPresent()) {
            System.out.println(notFound);

            return Optional.empty();
        }

        Optional<Order> order = dataProvider.getOrder(client.get().getOrders().getLast().getId());

        if (!order.isPresent()) {
            System.out.println(notFound);

            return Optional.empty();
        }

        System.out.println(found);

        OrderDTO orderDTO = orderMapper.toDTO(order.get());

        return Optional.of(orderDTO);
    }

    public Optional<Order.OrderEntry> deleteFromOrder(Long clientId, @Valid @NotNull Long productId) {

        Optional<Client> client = dataProvider.getClient(clientId);

        if (!client.isPresent()) {
            System.out.println(notFound);

            return Optional.empty();
        }

        Optional<Order> order = dataProvider.getOrder(client.get().getOrders().getLast().getId());

        if (!order.isPresent()) {
            System.out.println(notFound);

            return Optional.empty();
        }

        Optional<Order.OrderEntry> entry = order.get().getEntryList()
                .stream()
                .filter(e -> e.getProductId().equals(productId))
                .findFirst();

        Product product = dataProvider.getProduct(productId).get();

        product.setStock(product.getStock() + entry.get().getAmount());

        order.get().removeOrderEntry(entry.get());

        if (order.get().getEntryList().isEmpty()) {
            dataWriteService.deleteOrder(order.get());
            System.out.println("Order deleted.");

            return Optional.of(entry.get());
        }
        dataWriteService.saveProduct(product);

        dataWriteService.saveOrder(order.get());
        System.out.println("Deleted one product entry from order list.");

        return Optional.of(entry.get());
    }


    public Optional<Order.OrderEntry> addToOrder(Long productId, Integer amount, Long clientId) {

        if (amount == null || amount <= 0) {
            System.out.println("Invalid amount.");

            return Optional.empty();
        }

        try {
            Optional<Product> product = dataProvider.getProduct(productId);

            if (!product.isPresent()) {
                System.out.println("Product not found.");

                return Optional.empty();
            }

            Optional<Client> client = dataProvider.getClient(clientId);

            if (!client.isPresent()) {
                System.out.println("Client not found.");

                return Optional.empty();
            }

            Integer onStockProduct = product.get().getStock();

            if (amount > onStockProduct) {
                System.out.println("Not enough stock.");

                return Optional.empty();
            }

            Order currentOrder;

            Optional<Order> order = client.get()
                    .getOrders()
                    .stream()
                    .filter(e -> e.getOpen())
                    .findFirst();

            if (!order.isPresent()) {
                currentOrder = new Order();
                currentOrder.setClient(client.get());
                currentOrder.setOpen(true);
            } else {
                currentOrder = order.get();
            }

            Optional<Order.OrderEntry> entryFind = currentOrder.getEntryList()
                    .stream()
                    .filter(entry -> entry.getProductId().equals(productId))
                    .findFirst();

            if (entryFind.isPresent() && entryFind.get().getAmount() + amount <= onStockProduct) {

                Order.OrderEntry entry = entryFind.get();

                entry.setAmount(entry.getAmount() + amount);
                product.get().setStock(onStockProduct - amount);
                dataWriteService.saveProduct(product.get());
                dataWriteService.saveOrder(currentOrder);

                System.out.println("Order saved.");

                return Optional.of(entry);
            }

            Order.OrderEntry newEntry = new Order.OrderEntry(product.get().getId(), amount);

            System.out.println("All checks ok. Adding to current open order..");

            currentOrder.addOrderEntry(newEntry);
            product.get().setStock(onStockProduct - amount);
            dataWriteService.saveProduct(product.get());
            dataWriteService.saveOrder(currentOrder);

            System.out.println("Order saved.");

            return Optional.of(newEntry);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while adding to order: " + e.getMessage());

            return Optional.empty();
        }
    }

    public Optional<Order.OrderEntry> withdrawFromOrder(Long productId, int amount, Long clientId) {

        Optional<Product> product = dataProvider.getProduct(productId);

        if (!product.isPresent()) {
            System.out.println("Product not found.");

            return Optional.empty();
        }

        Optional<Client> client = dataProvider.getClient(clientId);

        if (!client.isPresent()) {
            System.out.println(notFound);

            return Optional.empty();
        }

        Optional<Order> order = dataProvider.getOrder(client.get().getOrders().getLast().getId());

        if (!order.isPresent() || !order.get().getOpen()) {
            System.out.println("Order not found or closed.");

            return Optional.empty();
        }

        Order currentOrder = order.get();
        Optional<Order.OrderEntry> entryFind = currentOrder.getEntryList().stream()
                .filter(entry -> entry.getProductId().equals(productId))
                .findFirst();

        Order.OrderEntry entry = entryFind.get();
        Integer onStockProduct = product.get().getStock();

        if (entryFind.isPresent() && amount < entry.getAmount()) {
            entry.setAmount(entry.getAmount() - amount);
            product.get().setStock(onStockProduct + amount);
            dataWriteService.saveProduct(product.get());
            dataWriteService.saveOrder(currentOrder);

            System.out.println("Order saved.");

            return Optional.of(entry);
        }

        entry.setAmount(entry.getAmount() - amount);
        dataWriteService.saveOrder(currentOrder);
        System.out.println("Withdrawn" + amount + "o" +
                "f product id" + entry.getProductId() + " from order entry.");

        return Optional.of(entry);
    }
}
