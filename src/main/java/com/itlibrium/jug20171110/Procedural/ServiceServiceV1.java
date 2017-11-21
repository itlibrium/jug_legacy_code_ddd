package com.itlibrium.jug20171110.Procedural;

import com.itlibrium.jug20171110.BusinessException;
import com.itlibrium.jug20171110.PricingCategory;
import com.itlibrium.jug20171110.SparePart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServiceServiceV1 {
    private IServiceRepository serviceRepository;
    private IClientRepository clientRepository;
    private IEngineerRepository engineerRepository;
    private ISparePartRepository sparePartRepository;

    public void finish(int serviceId, Collection<Integer> sparePartIds)
            throws BusinessException {
        Service service = serviceRepository.Get(serviceId);
        if (service.getStatus() != ServiceStatus.Scheduled) {
            throw new BusinessException("Nieprawidłowy status usługi");
        }

        List<SparePart> spareParts = new ArrayList<>();
        for (int sparePartId : sparePartIds) {
            spareParts.add(sparePartRepository.Get(sparePartId));
        }
        service.setSpareParts(spareParts);

        BigDecimal sparePartsCost = BigDecimal.valueOf(0);
        for (SparePart sparePart : service.getSpareParts()) {
            sparePartsCost = sparePartsCost.add(sparePart.Price);
        }

        PricingCategory pricingCategory =
                service.getClient().getEquipmentModel().getPricingCategory();

        BigDecimal labourCost = pricingCategory.getPricePerHour()
                .multiply(BigDecimal.valueOf(service.getDuration()));

        service.setPrice(pricingCategory.getMinPrice().max(labourCost)
                .add(sparePartsCost));

        service.setStatus(ServiceStatus.Done);
        serviceRepository.Save(service);
    }
}
