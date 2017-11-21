package com.itlibrium.jug.v1.controllers;

import com.itlibrium.jug.v1.entities.ServiceCategory;
import com.itlibrium.jug.v1.repositories.ServiceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/serviceCategories")
public class ServiceCategoryController {

    private final ServiceCategoryRepository clientRepository;

    @Autowired
    public ServiceCategoryController(ServiceCategoryRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @RequestMapping(method= RequestMethod.POST)
    public void createServiceCategory(@RequestBody ServiceCategory client) {
        clientRepository.save(client);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ServiceCategory getServiceCategory(@PathVariable(value = "id") int id) {
        return clientRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<ServiceCategory> getServiceCategories() {
        return clientRepository.findAll();
    }

}
