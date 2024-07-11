package com.pdev.ecdemo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "invoices")
@NoArgsConstructor @EqualsAndHashCode @Getter @Setter
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;
    private Double totalPrice;

    @ManyToOne @JoinColumn(name = "client_id")
    private Client clientId;

    @ElementCollection
    private List<InvoiceDetail> details = new ArrayList<>();

    @ManyToOne
    private Product productId;


    public Invoice(LocalDateTime createdAt, Double totalPrice, Client clientId, List<InvoiceDetail> details) {
        this.createdAt = createdAt;
        this.totalPrice = totalPrice;
        this.clientId = clientId;
        this.details = details;
    }

    //---------------------------------------------------------------------

    @Embeddable
    public static class InvoiceDetail {

        private String description;
        private Integer amount;
        private Double price;

        public InvoiceDetail(String description, Integer amount, Double price) {
            this.description = description;
            this.amount = amount;
            this.price = price;
        }

    }
}
