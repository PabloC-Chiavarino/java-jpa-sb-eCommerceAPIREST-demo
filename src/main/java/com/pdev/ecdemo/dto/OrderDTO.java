package com.pdev.ecdemo.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO extends _GenericDTO {

    private Boolean open;
    private Long invoiceId;
    private Long clientId;

    public OrderDTO(Long id, Boolean open, Long invoiceId, Long clientId) {
        super(id);
        this.open = open;
        this.invoiceId = invoiceId;
        this.clientId = clientId;
    }
}