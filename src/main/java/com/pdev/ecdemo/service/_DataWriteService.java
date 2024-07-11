package com.pdev.ecdemo.service;

import com.pdev.ecdemo.entity.*;
import com.pdev.ecdemo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class _DataWriteService {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public _DataWriteService(ClientRepository clientRepository, ProductRepository productRepository, OrderRepository orderRepository, InvoiceRepository invoiceRepository) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.invoiceRepository = invoiceRepository;
    }

    //----------------------------INVOICE-----------------------------------

    public Invoice saveInvoice(Invoice invoice) {

        return invoiceRepository.save(invoice);
    }

    //-----------------------------ORDER-------------------------------------

    public Order saveOrder(Order order) {

        return orderRepository.save(order);
    }

    public void deleteOrder(Order order) {

        orderRepository.delete(order);
    }

    //-----------------------------CLIENT-------------------------------------

    public Client saveClient(Client client) {

        return clientRepository.save(client);
    }

    //-----------------------------PRODUCT-------------------------------------

    public Product saveProduct(Product product) {

        return product = productRepository.save(product);
    }
}
