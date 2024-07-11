package com.pdev.ecdemo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

@Primary
@Entity
@Table(name = "clients")
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "Doc pass cannot be blank")
    private Long docPass;

    public Client(String name, String lastName, String email, Long docPass) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.docPass = docPass;
    }

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invoice> invoices = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
}
