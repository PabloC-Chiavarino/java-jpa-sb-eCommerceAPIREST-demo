package com.pdev.ecdemo.repository;

import com.pdev.ecdemo.entity.Client;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@Primary
public interface ClientRepository extends JpaRepository<Client, Long> {

    public Optional<Client> findByDocPass(Long docPass);

    public Optional<Client> findByLastName(String lastName);
}