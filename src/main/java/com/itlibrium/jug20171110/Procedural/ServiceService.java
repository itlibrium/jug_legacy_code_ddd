package com.itlibrium.jug20171110.Procedural;

import com.itlibrium.jug20171110.BusinessException;
import com.itlibrium.jug20171110.EquipmentModel;
import com.itlibrium.jug20171110.PricingCategory;
import com.itlibrium.jug20171110.SparePart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServiceService {
    private IServiceRepository serviceRepository;
    private IClientRepository clientRepository;
    private IEngineerRepository engineerRepository;
    private ISparePartRepository sparePartRepository;
    private IServiceContractRepository serviceContractRepository;

    public void finish(int serviceId, Collection<Integer> sparePartIds) throws BusinessException {
        Service service = serviceRepository.Get(serviceId);
        if (service.getStatus() != ServiceStatus.Scheduled)
            throw new BusinessException("Nieprawidłowy status usługi");

        List<SparePart> spareParts = new ArrayList<>();
        for (int sparePartId : sparePartIds)
        {
            spareParts.add(sparePartRepository.Get(sparePartId));
        }
        service.setSpareParts(spareParts);

        BigDecimal price = BigDecimal.ZERO;
        if (service.isWarranty())
        {
            price = BigDecimal.valueOf(0);
        }
        else
        {
            EquipmentModel equipmentModel = service.getClient().getEquipmentModel();
            PricingCategory pricingCategory = equipmentModel.getPricingCategory();
            Contract contract = service.getClient().getContract();

            BigDecimal sparePartsCost = BigDecimal.valueOf(0);
            for (SparePart sparePart : service.getSpareParts())
            {
                sparePartsCost = sparePartsCost.add(sparePart.Price);
            }

            if (contract == null)
            {
                price = pricingCategory.getMinPrice().max(pricingCategory.getPricePerHour()
                        .multiply(BigDecimal.valueOf(service.getDuration()))).add(sparePartsCost);
            }
            else
            {
                BigDecimal sparePartsCostLimit = contract.getSparePartsCostLimit()
                        .subtract(contract.getSparePartsCostLimitUsed());
                if (sparePartsCostLimit.compareTo(sparePartsCost) >= 0)
                {
                    contract.setSparePartsCostLimitUsed(contract.getSparePartsCostLimitUsed().subtract(sparePartsCost));
                    sparePartsCost = BigDecimal.ZERO;
                }
                else
                {
                    contract.setSparePartsCostLimitUsed(BigDecimal.ZERO);
                    sparePartsCost = sparePartsCost.subtract(sparePartsCostLimit);
                }
                price = price.add(sparePartsCost);

                if (contract.getFreeInterventionsUsed() < contract.getFreeInterventions())
                {
                    contract.setFreeInterventionsUsed(contract.getFreeInterventionsUsed() + 1);

                    int freeInterventionTimeLimit = equipmentModel.getFreeInterventionTimeLimit();
                    if (service.getDuration() > freeInterventionTimeLimit)
                    {
                        price = price.add(pricingCategory.getPricePerHour()
                                .multiply(BigDecimal.valueOf(service.getDuration() - freeInterventionTimeLimit)));
                    }
                }
                else
                {
                    price = price.add(pricingCategory.getMinPrice().max(pricingCategory.getPricePerHour()
                            .multiply(BigDecimal.valueOf(service.getDuration()))));
                }

                serviceContractRepository.Save(contract);
            }
        }

        service.setPrice(price);
        service.setStatus(ServiceStatus.Done);
        serviceRepository.Save(service);
    }
}
