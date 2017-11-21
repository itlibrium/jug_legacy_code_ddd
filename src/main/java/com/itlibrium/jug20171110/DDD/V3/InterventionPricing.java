package com.itlibrium.jug20171110.DDD.V3;

import lombok.Getter;

public class InterventionPricing {
    @Getter
    private final  Money totalPrice;
    @Getter
    private final ContractLimits contractLimits;

    public InterventionPricing(Money totalPrice, ContractLimits contractLimits) {
        this.totalPrice = totalPrice;
        this.contractLimits = contractLimits;
    }
}
