package com.itlibrium.jug20171110.DDD.V3;

import com.itlibrium.jug20171110.BusinessException;

public class FinishInterventionHandler {
    private final IInterventionRepository interventionRepository;
    private final IContractRepository contractRepository;
    private final IPricingService pricingService;

    public FinishInterventionHandler(IInterventionRepository interventionRepository, IContractRepository contractRepository, IPricingService pricingService) {
        this.interventionRepository = interventionRepository;
        this.contractRepository = contractRepository;
        this.pricingService = pricingService;
    }

    public void finish(FinishInterventionCommand command) throws BusinessException {
        Intervention intervention = interventionRepository.Get(command.getInterventionId());

        IContract contract = contractRepository.GetForClient(intervention.getClientId());
        ContractLimits contractLimits = contract.GetContractLimits();

        InterventionPricing interventionPricing =
                pricingService.GetPricingFor(intervention, command.getServiceActions(), contractLimits);

        intervention.Finish(command.getServiceActions(), interventionPricing);
        interventionRepository.Save(intervention);

        contract.AddUsage(interventionPricing.getContractLimits());
        contractRepository.Save(contract);
    }
}

