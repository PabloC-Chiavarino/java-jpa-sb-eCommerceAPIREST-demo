package com.pdev.ecdemo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoices")
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @NotNull(message = "Client parameter cannot be blank")
    private Client client;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ElementCollection
    @CollectionTable(name = "invoice_details", joinColumns = @JoinColumn(name = "invoice_id"))
    private List<InvoiceDetail> invoiceDetails = new ArrayList<>();

    public Invoice(LocalDateTime createdAt, Double totalPrice, Client clientId, Order orderId, List<InvoiceDetail> invoiceDetails) {
        this.createdAt = createdAt;
        this.totalPrice = totalPrice;
        this.client = clientId;
        this.order = orderId;
        this.invoiceDetails = invoiceDetails;
    }

    @Embeddable
    @Getter
    @Setter
    public static class InvoiceDetail {

        @Column(name = "description", length = 50)
        private String description;

        @Column(name = "amount")
        private Integer amount;

        @Column(name = "price")
        private Double price;

        public InvoiceDetail() {
        }

        public InvoiceDetail(String description, Integer amount, Double price) {
            this.description = description;
            this.amount = amount;
            this.price = price;
        }
    }
}
