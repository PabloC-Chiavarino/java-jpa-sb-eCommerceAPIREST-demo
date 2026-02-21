package com.pdev.ecdemo.config;

import com.pdev.ecdemo.entity.Client;
import com.pdev.ecdemo.entity.Invoice;
import com.pdev.ecdemo.entity.Order;
import com.pdev.ecdemo.entity.Product;
import com.pdev.ecdemo.repository.ClientRepository;
import com.pdev.ecdemo.repository.InvoiceRepository;
import com.pdev.ecdemo.repository.OrderRepository;
import com.pdev.ecdemo.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DemoDataLoader implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final InvoiceRepository invoiceRepository;

    public DemoDataLoader(
            ClientRepository clientRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository,
            InvoiceRepository invoiceRepository) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public void run(String... args) {

        // PRODUCTS
        Product p1 = new Product("Apple", "iPhone 14", 1001L, 10, 999.0);
        Product p2 = new Product("Samsung", "Galaxy S23", 1002L, 15, 899.0);
        Product p3 = new Product("Sony", "Headphones WH-1000XM5", 1003L, 20, 399.0);

        productRepository.saveAll(List.of(p1, p2, p3));

        // CLIENT
        Client client = new Client("Juan", "Perez", "juan@mail.com", 12345678L);
        clientRepository.save(client);

        // OPEN ORDER
        Order openOrder = new Order();
        openOrder.setOpen(true);
        openOrder.setClient(client);
        openOrder.addOrderEntry(new Order.OrderEntry(p1.getId(), 1));
        openOrder.addOrderEntry(new Order.OrderEntry(p3.getId(), 2));
        orderRepository.save(openOrder);

        // CLOSED ORDER + INVOICE
        Order closedOrder = new Order();
        closedOrder.setOpen(false);
        closedOrder.setClient(client);
        closedOrder.addOrderEntry(new Order.OrderEntry(p2.getId(), 1));
        orderRepository.save(closedOrder);

        Invoice.InvoiceDetail detail = new Invoice.InvoiceDetail("Samsung Galaxy S23", 1, 899.0);

        Invoice invoice = new Invoice(
                LocalDateTime.now(),
                899.0,
                client,
                closedOrder,
                List.of(detail)
        );

        invoiceRepository.save(invoice);

        // Relacionar invoice con la orden cerrada
        closedOrder.setInvoice(invoice);
        orderRepository.save(closedOrder);
    }
}