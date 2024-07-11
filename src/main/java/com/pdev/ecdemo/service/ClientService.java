package com.pdev.ecdemo.service;

import com.pdev.ecdemo.dto.ClientDTO;
import com.pdev.ecdemo.entity.Client;
import com.pdev.ecdemo.mapper.ClientMapper;
import com.pdev.ecdemo.repository.ClientRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter
public class ClientService extends _GenericService<Client, ClientDTO> {

    private final String found = "Client found";
    private final String notFound = "Client not found";

    private final _DataProviderService dataProvider;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, _DataProviderService dataProvider, ClientMapper clientMapper) {
        super(clientRepository, clientMapper);
        this.dataProvider = dataProvider;
        this.clientMapper = clientMapper;
    }

    public Optional<ClientDTO> findByDocPass(Long docPass) {

        Optional<Client> client = dataProvider.getClientByDocPass(docPass);
        if (client.isPresent()) {
            System.out.println(found);

            ClientDTO clientDTO = clientMapper.toDTO(client.get());

            return Optional.of(clientDTO);

        } else {
            System.out.println(notFound);

            return Optional.empty();
        }
    }

    public Optional<ClientDTO> findByLastName(String lastName) {

        Optional<Client> client = dataProvider.getClientByLastName(lastName);
        if (client.isPresent()) {
            System.out.println(found);

            ClientDTO clientDTO = clientMapper.toDTO(client.get());

            return Optional.of(clientDTO);
        } else {
            System.out.println(notFound);

            return Optional.empty();
        }
    }

    public List<ClientDTO> findAll() {

        return clientMapper.toDTOList(dataProvider.getAll());
    }
}
