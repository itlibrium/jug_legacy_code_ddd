package com.itlibrium.jug.v1.controllers;

import com.itlibrium.jug.v1.entities.Serviceman;
import com.itlibrium.jug.v1.repositories.ServicemanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicemen")
public class ServicemanController {

    private final ServicemanRepository clientRepository;

    @Autowired
    public ServicemanController(ServicemanRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @RequestMapping(method= RequestMethod.POST)
    public void createServiceman(@RequestBody Serviceman client) {
        clientRepository.save(client);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Serviceman getServiceman(@PathVariable(value = "id") int id) {
        return clientRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Serviceman> getClients() {
        return clientRepository.findAll();
    }

}
