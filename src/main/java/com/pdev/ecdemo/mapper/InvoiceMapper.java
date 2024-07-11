package com.pdev.ecdemo.mapper;

import com.pdev.ecdemo.dto.InvoiceDTO;
import com.pdev.ecdemo.dto.InvoiceDTO.InvoiceDetailDTO;
import com.pdev.ecdemo.entity.Client;
import com.pdev.ecdemo.entity.Invoice;
import com.pdev.ecdemo.entity.Order;
import com.pdev.ecdemo.service._DataProviderService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Getter
public class InvoiceMapper extends _GenericMapper<Invoice, InvoiceDTO> {

    private final _DataProviderService dataProvider;

    @Autowired
    public InvoiceMapper(_DataProviderService dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public InvoiceDTO toDTO(Invoice invoice) {

        System.out.println("Mapping Invoice ID: " + invoice.getId());

        List<InvoiceDetailDTO> detailsDTO = invoice.getInvoiceDetails()
                .stream()
                .map(detail -> new InvoiceDetailDTO(
                        detail.getDescription(),
                        detail.getAmount(),
                        detail.getPrice()

                ))
                .collect(Collectors.toList());

        return new InvoiceDTO(
                invoice.getId(),
                invoice.getCreatedAt(),
                invoice.getClient().getId(),
                invoice.getOrder().getId(),
                invoice.getTotalPrice(),
                detailsDTO
        );
    }

    @Override
    public Invoice toEntity(InvoiceDTO invoiceDTO) {

        Client client = dataProvider.getClient(invoiceDTO.getId()).get();
        Order order = dataProvider.getOrder(invoiceDTO.getOrderId()).get();

        List<Invoice.InvoiceDetail> invoiceDetails = invoiceDTO.getInvoiceDetails()
                .stream()
                .map(detailDTO -> new Invoice.InvoiceDetail(
                        detailDTO.getDescription(),
                        detailDTO.getAmount(),
                        detailDTO.getPrice()
                ))
                .collect(Collectors.toList());


        return new Invoice(
                invoiceDTO.getCreatedAt(),
                invoiceDTO.getTotalPrice(),
                client,
                order,
                invoiceDetails
        );
    }
}
