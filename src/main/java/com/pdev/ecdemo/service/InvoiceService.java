package com.pdev.ecdemo.service;

import com.pdev.ecdemo.dto.InvoiceDTO;
import com.pdev.ecdemo.entity.*;
import com.pdev.ecdemo.mapper.*;
import com.pdev.ecdemo.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService extends _GenericService<Invoice, InvoiceDTO> {

    private final _DataWriteService dataPersisterService;
    private final _DataProviderService dataProvider;
    private final InvoiceMapper invoiceMapper;
    private final ClientMapper clientMapper;
    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository,
                          _DataProviderService dataProvider,
                          _DataWriteService dataPersisterService,
                          InvoiceMapper invoiceMapper,
                          ClientMapper clientMapper,
                          OrderMapper orderMapper,
                          ProductMapper productMapper) {

        super(invoiceRepository, invoiceMapper);
        this.dataProvider = dataProvider;
        this.dataPersisterService = dataPersisterService;
        this.invoiceMapper = invoiceMapper;
        this.clientMapper = clientMapper;
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
    }

    public Optional<InvoiceDTO> createInvoice(Long clientId) {

        Optional<Client> client = dataProvider.getClient(clientId);

        if (!client.isPresent()) {
            System.out.println("Client not found.");

            return Optional.empty();
        }

        System.out.println("Client found.");

        Optional<Order> order = dataProvider.getOrder(client.get().getOrders().get(client.get().getOrders().size() - 1).getId());

        if (!order.isPresent()) {
            System.out.println("Order not found.");

            return Optional.empty();
        }

        System.out.println("Order found.");

        List<Order.OrderEntry> entryList = order.get().getEntryList();
        List<Invoice.InvoiceDetail> invoiceDetails = new ArrayList<>();
        Double totalPrice = 0.0;

        for (Order.OrderEntry entry : entryList) {

            Optional<Product> product = dataProvider.getProduct(entry.getProductId());

            String description = product.get().getDescription();
            Integer amount = entry.getAmount();
            Double price = product.get().getPrice();
            invoiceDetails.add(new Invoice.InvoiceDetail(description, amount, price));
            totalPrice += amount * price;
        }

        Invoice invoice = new Invoice(LocalDateTime.now(), totalPrice, client.get(), order.get(), invoiceDetails);

        order.get().setOpen(false);
        dataPersisterService.saveOrder(order.get());

        System.out.println("Order closed and saved:");

        dataPersisterService.saveInvoice(invoice);

        System.out.println("Invoice created and saved.");

        InvoiceDTO invoiceDTO = invoiceMapper.toDTO(invoice);
        System.out.println("Invoice Details mapping result: " + invoiceDTO.getInvoiceDetails());


        return Optional.of(invoiceDTO);
    }

    public Optional<List<InvoiceDTO>> findByClient(Long clientId) {

        Optional<Client> client = dataProvider.getClient(clientId);
        if (!client.isPresent()) {
            System.out.println("Client not found.");

            return Optional.empty();
        }

        System.out.println("Client found.");

        Optional<List<Invoice>> invoices = dataProvider.getInvoices(client.get().getId());

        if (!invoices.isPresent()) {
            System.out.println("Invoice not found.");

            return Optional.empty();
        } else {
            System.out.println("Invoice/s found.");

            List<InvoiceDTO> invoiceDTOList = invoiceMapper.toDTOList(invoices.get());

            return Optional.of(invoiceDTOList);
        }
    }
}
