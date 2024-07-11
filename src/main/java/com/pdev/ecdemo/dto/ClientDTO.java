package com.pdev.ecdemo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClientDTO extends _GenericDTO {

    private String name;
    private String lastName;
    private Long docPass;
    private String email;
    private List<InvoiceDTO> invoices;
    private OrderDTO order;

    public ClientDTO(Long id,
                     String name,
                     String lastName,
                     Long docPass,
                     String email,
                     List<InvoiceDTO> invoices,
                     OrderDTO order) {
        super(id);
        this.name = name;
        this.lastName = lastName;
        this.docPass = docPass;
        this.email = email;
        this.invoices = invoices;
        this.order = order;
    }
}
