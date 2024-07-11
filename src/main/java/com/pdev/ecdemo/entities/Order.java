package com.pdev.ecdemo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "Orders") @Getter @Setter
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<OrderEntry> entryList;

    private Boolean open;

    public Order() {
        this.entryList = new ArrayList<>();
        this.open = true;
    }

    public void addOrderEntry(Order.OrderEntry OrderEntry) {
        this.entryList.add(OrderEntry);
    }

    public void removeOrderEntry(Order.OrderEntry OrderEntry) {
        this.entryList.remove(OrderEntry);
    }


    @ManyToOne
    private Client clientId;

    @ManyToOne
    private Product productId;

    //-----------------------------------------------

    @Getter @Setter @Embeddable
    public static class OrderEntry {

        @NotNull
        @Column(name = "product_id", columnDefinition = "BIGINT")
        private Long productId;

        @NotNull
        @Column(name = "amount", columnDefinition = "INT")
        private Integer amount;

        @NotNull
        @Column(name = "client_id", columnDefinition = "BIGINT")
        private Long clientId;


        public OrderEntry(Long productId, int amount, Long clientId) {
            this.productId = productId;
            this.amount = amount;
            this.clientId = clientId;
        }
    }
}