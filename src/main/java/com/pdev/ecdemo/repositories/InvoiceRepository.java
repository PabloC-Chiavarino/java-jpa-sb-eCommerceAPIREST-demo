package com.pdev.ecdemo.repositories;

import com.pdev.ecdemo.entities.Client;
import com.pdev.ecdemo.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    public List<Invoice> findByClientId(Client clientId);

}