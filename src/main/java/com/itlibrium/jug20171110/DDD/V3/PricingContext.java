package com.itlibrium.jug20171110.DDD.V3;

import lombok.Getter;

public class PricingContext
{
    @Getter
    private final ServiceAction serviceAction;
    @Getter
    private final ContractLimits contractLimits;

    public PricingContext(ServiceAction serviceAction, ContractLimits contractLimits) {
        this.serviceAction = serviceAction;
        this.contractLimits = contractLimits;
    }
}
