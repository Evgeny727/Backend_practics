package com.graphql.api.shop.controllers;

import com.graphql.api.shop.models.users.Client;
import com.graphql.api.shop.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Component
@Controller
@CrossOrigin
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @MutationMapping
    public Client createClient(@Argument Client client) {
        return clientService.save(client);
    }

    @MutationMapping
    public Client updateClientById(@Argument Long id, @Argument Client client) {
        return clientService.updateById(id, client);
    }

    @MutationMapping
    public void deleteClientById(@Argument Long id) {
        clientService.deleteById(id);
    }

    @QueryMapping
    public Client findClientById(@Argument Long id) {
        return clientService.findById(id);
    }

    @QueryMapping
    public List<Client> findAllClients() {
        return clientService.findAll();
    }

}
