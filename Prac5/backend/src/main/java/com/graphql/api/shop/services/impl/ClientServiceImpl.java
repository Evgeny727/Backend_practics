package com.graphql.api.shop.services.impl;

import com.graphql.api.shop.models.users.Client;
import com.graphql.api.shop.models.users.User;
import com.graphql.api.shop.repositories.ClientRepository;
import com.graphql.api.shop.services.ClientService;
import com.graphql.api.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final UserService userService;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, UserService userService) {
        this.clientRepository = clientRepository;
        this.userService = userService;
    }

    @Override
    public Client save(Client client) {
        client.setUser(userService.save(client.getUser()));
        return clientRepository.save(client);
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public Client updateById(Long id, Client client) {
        Client lastClient = findById(id);
        client.getUser().setId(lastClient.getUser().getId());
        lastClient.setUser(
                userService.updateById(
                        lastClient.getUser().getId(),
                        client.getUser()
                )
        );
        return clientRepository.save(lastClient);
    }

    @Override
    public List<Client> saveAll(List<Client> clients) {
        return clients.stream().map(this::save).toList();
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        User user = findById(id).getUser();
        clientRepository.deleteById(id);
        userService.deleteById(user.getId());
    }
}
