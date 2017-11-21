package com.itlibrium.jug20171110.DDD.V1;

import com.itlibrium.jug20171110.BusinessException;

public class FinishInterventionHandler {
    private final IInterventionRepository interventionRepository;
    private final IPricePolicyFactory pricePolicyFactory;

    public FinishInterventionHandler(IInterventionRepository interventionRepository, IPricePolicyFactory pricePolicyFactory) {
        this.interventionRepository = interventionRepository;
        this.pricePolicyFactory = pricePolicyFactory;
    }

    public void finish(FinishInterventionCommand command) throws BusinessException {
        Intervention intervention = interventionRepository.get(command.getInterventionId());
        PricePolicy pricePolicy = this.pricePolicyFactory.createFor(intervention);
        intervention.finish(command.getServiceActions(), pricePolicy);
        interventionRepository.save(intervention);
    }
}

