package com.pdev.ecdemo.services;

import com.pdev.ecdemo.entities.Client;
import com.pdev.ecdemo.entities.Invoice;
import com.pdev.ecdemo.entities.Order;
import com.pdev.ecdemo.entities.Product;
import com.pdev.ecdemo.repositories.ClientRepository;
import com.pdev.ecdemo.repositories.InvoiceRepository;
import com.pdev.ecdemo.repositories.OrderRepository;
import com.pdev.ecdemo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService extends _GenericService<Invoice> {

    private final InvoiceRepository repository;
    private final ProductRepository productRepository;
    private final OrderRepository OrderRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public InvoiceService(InvoiceRepository repository , ProductRepository productRepository, OrderRepository OrderRepository, ClientRepository clientRepository) {
        super(repository);
        this.repository = repository;
        this.productRepository = productRepository;
        this.OrderRepository = OrderRepository;
        this.clientRepository = clientRepository;
    }


    /*public List<Invoice> findAll() {
        return repository.findAll();
    }*/

    public Invoice createInvoice(Long clientId) {
        Client client = clientRepository.findById(clientId).get();
        Order order = OrderRepository.findByClientId(client).get();

        Double temporalTotalPrice = 0.0;
        List<Invoice.InvoiceDetail> temporalInvoiceDetails = new ArrayList<>();

        for (Order.OrderEntry entry : order.getEntryList()) {

            Product product = productRepository.findById(entry.getProductId()).orElseThrow(() -> new IllegalArgumentException("Product not found"));

            String description = product.getDescription();
            Double price = product.getPrice();
            Integer amount = entry.getAmount();

            temporalTotalPrice += amount * price;

            Invoice.InvoiceDetail invoiceDetail = new Invoice.InvoiceDetail(description, amount, price);
            temporalInvoiceDetails.add(invoiceDetail);
        }

        Invoice invoice = new Invoice(LocalDateTime.now(), temporalTotalPrice, client, temporalInvoiceDetails);

        order.setOpen(false);

        System.out.println("Invoice created.");
        System.out.println("Order closed.");

        return repository.save(invoice);
    }

    public Optional<Invoice> findById(Long id) {
        Optional<Invoice> invoiceCheck = repository.findById(id);
        if (invoiceCheck.isPresent()) {
            System.out.println("Invoice found.");
            return invoiceCheck;
        } else {
            System.out.println("Invoice not found.");
            return Optional.empty();
        }
    }

    public List<Invoice> findByClient(Client clientId) {
        List<Invoice> invoiceCheck = repository.findByClientId(clientId);

        return invoiceCheck;
    }

   /* public Optional<Invoice> deleteInvoice(Long id) {
        Optional<Invoice> invoiceCheck = repository.findById(id);
        if (invoiceCheck.isPresent()) {
            repository.deleteById(id);
            System.out.println("Invoice deleted.");
            return invoiceCheck;
        } else {
            System.out.println("Invoice not found.");
            return Optional.empty();
        }
    }*/

}
