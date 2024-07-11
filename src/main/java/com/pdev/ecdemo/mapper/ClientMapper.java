package com.pdev.ecdemo.mapper;

import com.pdev.ecdemo.dto.ClientDTO;
import com.pdev.ecdemo.dto.InvoiceDTO;
import com.pdev.ecdemo.dto.OrderDTO;
import com.pdev.ecdemo.entity.Client;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
public class ClientMapper extends _GenericMapper<Client, ClientDTO> {

    @Override
    public ClientDTO toDTO(Client client) {

        System.out.println("Mapping Client ID: " + client.getId());

        List<InvoiceDTO> invoicesDTOList = client.getInvoices()
                .stream()
                .map(invoice -> {

                    InvoiceDTO dto = new InvoiceDTO();

                    dto.setId(invoice.getId());
                    dto.setCreatedAt(invoice.getCreatedAt());

                    return dto;

                })
                .collect(Collectors.toList());

        List<InvoiceDTO> invoicesDTO = invoicesDTOList.isEmpty() ? List.of() : invoicesDTOList;

        OrderDTO openOrderDTO = client.getOrders()
                .stream()
                .filter(order -> order.getOpen())
                .reduce((a, b) -> b)
                .map(order -> {

                    OrderDTO dto = new OrderDTO();

                    dto.setId(order.getId());
                    dto.setOpen(order.getOpen());

                    return dto;

                }).orElse(null);


        return new ClientDTO(
                client.getId(),
                client.getName(),
                client.getLastName(),
                client.getDocPass(),
                client.getEmail(),
                invoicesDTO,
                openOrderDTO
        );
    }

    @Override
    public Client toEntity(ClientDTO clientDTO) {

        return new Client(
                clientDTO.getName(),
                clientDTO.getLastName(),
                clientDTO.getEmail(),
                clientDTO.getDocPass()
        );
    }
}
