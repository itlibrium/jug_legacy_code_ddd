package com.itlibrium.jug.v1.controllers;

import com.itlibrium.jug.v1.entities.*;
import com.itlibrium.jug.v1.repositories.ClientRepository;
import com.itlibrium.jug.v1.repositories.ServiceCategoryRepository;
import com.itlibrium.jug.v1.repositories.ServiceRepository;
import com.itlibrium.jug.v1.repositories.ServicemanRepository;
import com.itlibrium.jug.v1.services.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ClientRepository clientRepository;
    private final ServiceCategoryRepository serviceCategoryRepository;
    private final ServicemanRepository servicemanRepository;
    private final ServiceRepository serviceRepository;
    private final SchedulingService schedulingService;

    @Autowired
    public ServiceController(ClientRepository clientRepository, ServiceCategoryRepository serviceCategoryRepository,
                             ServicemanRepository servicemanRepository, ServiceRepository serviceRepository, SchedulingService schedulingService) {
        this.clientRepository = clientRepository;
        this.serviceCategoryRepository = serviceCategoryRepository;
        this.servicemanRepository = servicemanRepository;
        this.serviceRepository = serviceRepository;
        this.schedulingService = schedulingService;
    }


    @RequestMapping(method= RequestMethod.POST)
    public void createService(@RequestParam int clientId,
                              @RequestParam int serviceCategoryId,
                              @RequestParam int servicemanId,
                              @RequestParam long start,
                              @RequestParam long end ){


        Client client = clientRepository.findOne(clientId);
        ServiceCategory serviceCategory = serviceCategoryRepository.findOne(serviceCategoryId);
        Serviceman serviceman = servicemanRepository.findOne(servicemanId);

        Service service = new Service();
        service.setClient(client);
        service.setServiceCategory(serviceCategory);
        service.setServiceman(serviceman);
        service.setStatus(ServiceStatus.NEW);

        validate(serviceman, service);

        schedulingService.scheduleMeeting(Instant.ofEpochMilli(start), Instant.ofEpochMilli(end),
                service.getClient().getName() + " " + service.getServiceCategory().getName());

        service.setStatus(ServiceStatus.SCHEDULED);
        serviceRepository.save(service);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Service getService(@PathVariable(value = "id") int id) {
        return serviceRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Service> getServices() {
        return serviceRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateService(Service service) {
        //TODO Zdecydować czy usiłujemy być hiper RESTful i robimy przez update biorący cały serwis
        //TODO czy na razie darujemy sobie wprowadzanie legacy przez RESTful i robimy API czasownikowe
        //TODO Fakt faktem legacy często jest spowodowany full RESTful podejściem na wczesnym etapie
    }


    private void validate(Serviceman serviceman, Service service) {
        if (!service.getServiceCategory().getRequiredCertificates().stream()
                .allMatch( rc -> serviceman.getCertificates().stream()
                        .filter(c -> c.getCertificateCategory().getCertificateType() == CertificateType.GENERAL)
                        .anyMatch(c -> c.getId() == rc.getId() && c.getExpiresOn().isAfter(Instant.now()))))
            throw new IllegalArgumentException("Brak uprawnień do kategorii usługi");


        if (!service.getClient().getEquipments().stream()
                .flatMap( e -> e.getEquipmentType().getRequiredCertificates().stream())
                .allMatch( rc -> serviceman.getCertificates().stream()
                        .filter(c -> c.getCertificateCategory().getCertificateType() == CertificateType.EQUIPMENT)
                        .anyMatch(c -> c.getId() == rc.getId() && c.getExpiresOn().isAfter(Instant.now()))))
            throw new IllegalArgumentException("Brak uprawnień do typu urządzenia");
    }
}
