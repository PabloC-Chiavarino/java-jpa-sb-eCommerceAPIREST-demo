package com.pdev.ecdemo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean open;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ElementCollection
    @CollectionTable(name = "order_entries", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderEntry> entryList;

    public Order() {
        this.entryList = new ArrayList<>();
    }

    public Order(Boolean open, Invoice invoice, Client client) {
        this.open = open;
        this.invoice = invoice;
        this.client = client;
    }

    public void addOrderEntry(Order.OrderEntry OrderEntry) {

        this.entryList.add(OrderEntry);
    }

    public void removeOrderEntry(Order.OrderEntry OrderEntry) {

        this.entryList.remove(OrderEntry);
    }

    @Getter
    @Setter
    @Embeddable
    @EqualsAndHashCode
    public static class OrderEntry {

        @NotNull(message = "Product cannot be blank")
        @Column(name = "product_id", columnDefinition = "BIGINT")
        private Long productId;

        @Column(name = "amount", columnDefinition = "INTEGER")
        private Integer amount;

        public OrderEntry() {
        }

        public OrderEntry(Long productId, Integer amount) {
            this.productId = productId;
            this.amount = amount;
        }
    }
}