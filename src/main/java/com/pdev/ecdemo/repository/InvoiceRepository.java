package com.pdev.ecdemo.repository;

import com.pdev.ecdemo.entity.Client;
import com.pdev.ecdemo.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    public Optional<List<Invoice>> findByClient(Client clientId);
}