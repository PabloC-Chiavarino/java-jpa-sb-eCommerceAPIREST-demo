package com.pdev.ecdemo.service;

import com.pdev.ecdemo.entity.Client;
import com.pdev.ecdemo.entity.Invoice;
import com.pdev.ecdemo.entity.Order;
import com.pdev.ecdemo.entity.Product;
import com.pdev.ecdemo.repository.ClientRepository;
import com.pdev.ecdemo.repository.InvoiceRepository;
import com.pdev.ecdemo.repository.OrderRepository;
import com.pdev.ecdemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class _DataProviderService {

    private final InvoiceRepository invoiceRepository;
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    @Autowired
    public _DataProviderService(InvoiceRepository invoiceRepository, OrderRepository orderRepository, ClientRepository clientRepository, ProductRepository productRepository) {
        this.invoiceRepository = invoiceRepository;
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    //----------------------------INVOICES-----------------------------------

    public Optional<Invoice> getInvoice(Long orderId) {
        Optional<Invoice> invoice = invoiceRepository.findById(orderId);

        return invoice;
    }

    public Optional<List<Invoice>> getInvoices(Long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        Optional<List<Invoice>> invoices = invoiceRepository.findByClient(client.get());

        return invoices;
    }

    //-----------------------------ORDERS-------------------------------------

    public Optional<Order> getOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);

        return order;
    }

    public Optional<List<Order.OrderEntry>> getOrderEntries(Long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        Optional<Order> order = orderRepository.findByClient(client.get());
        List<Order.OrderEntry> orderEntries = order.get().getEntryList();

        return Optional.of(orderEntries);
    }

    //-----------------------------CLIENTS-------------------------------------

    public Optional<Client> getClient(Long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);

        return client;
    }

    public Optional<Client> getClientByDocPass(Long docPass) {
        Optional<Client> client = clientRepository.findByDocPass(docPass);

        return client;
    }

    public Optional<Client> getClientByLastName(String lastName) {
        Optional<Client> client = clientRepository.findByLastName(lastName);

        return client;
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    //-----------------------------PRODUCTS-------------------------------------

    public Optional<Product> getProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        return product;
    }

    public Optional<Product> getProductByCode(Long code) {

        return productRepository.findByCode(code);
    }

    public Optional<Product> getProductByDescription(String description) {

        return productRepository.findByDescription(description);
    }

    public Optional<Product> getProductByBrand(String brand) {

        return productRepository.findByBrand(brand);
    }
}
