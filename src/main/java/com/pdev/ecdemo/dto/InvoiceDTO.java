package com.pdev.ecdemo.dto;

import com.pdev.ecdemo.entity.Invoice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceDTO extends _GenericDTO {

    private LocalDateTime createdAt;
    private Long clientId;
    private Long orderId;
    private Double totalPrice;
    private List<InvoiceDetailDTO> invoiceDetails;

    public InvoiceDTO(Long id,
                      LocalDateTime createdAt,
                      Long clientId,
                      Long orderId,
                      Double totalPrice,
                      List<InvoiceDetailDTO> invoiceDetails) {
        super(id);
        this.createdAt = createdAt;
        this.clientId = clientId;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.invoiceDetails = invoiceDetails;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class InvoiceDetailDTO {

        private String description;
        private Integer amount;
        private Double price;

        public InvoiceDetailDTO(String description, Integer amount, Double price) {
            this.description = description;
            this.amount = amount;
            this.price = price;
        }
    }
}

