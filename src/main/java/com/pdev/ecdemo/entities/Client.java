package com.pdev.ecdemo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity @Table(name = "clients")
@NoArgsConstructor @EqualsAndHashCode @Getter @Setter
public class Client {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;
    private String email;
    private Long docPass;


    public Client(String name, String lastName, String email, Long docPass) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.docPass = docPass;
    }

    @OneToMany(mappedBy = "clientId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Invoice> invoices;
    @OneToMany(mappedBy = "clientId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Order> orders;
}
