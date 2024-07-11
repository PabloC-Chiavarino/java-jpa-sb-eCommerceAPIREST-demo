package com.pdev.ecdemo.mapper;

import com.pdev.ecdemo.dto.OrderDTO;
import com.pdev.ecdemo.entity.Client;
import com.pdev.ecdemo.entity.Order;
import com.pdev.ecdemo.entity.Invoice;
import com.pdev.ecdemo.service._DataProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper extends _GenericMapper<Order, OrderDTO> {

    private final _DataProviderService dataProvider;

    @Autowired
    public OrderMapper(_DataProviderService dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public OrderDTO toDTO(Order order) {

        System.out.println("Mapping Order ID: " + order.getId());

        return new OrderDTO(
                order.getId(),
                order.getOpen(),
                order.getInvoice().getId(),
                order.getClient().getId()
        );
    }

    @Override
    public Order toEntity(OrderDTO orderDTO) {

        Invoice invoice = dataProvider.getInvoice(orderDTO.getId()).get();
        Client client = dataProvider.getClient(orderDTO.getId()).get();

        return new Order(
                orderDTO.getOpen(),
                invoice,
                client
        );
    }
}
