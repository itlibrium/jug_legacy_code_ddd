package com.itlibrium.jug.v1.controllers;

import com.itlibrium.jug.v1.entities.Client;
import com.itlibrium.jug.v1.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @RequestMapping(method= RequestMethod.POST)
    public void createClient(@RequestBody Client client) {
        clientRepository.save(client);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Client> getClients() {
        return clientRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Client getClient(@PathVariable(value = "id") int id) {
        return clientRepository.findOne(id);
    }

}
