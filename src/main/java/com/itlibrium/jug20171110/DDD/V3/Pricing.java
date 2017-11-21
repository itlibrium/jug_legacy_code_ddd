package com.itlibrium.jug20171110.DDD.V3;

import lombok.Getter;

public class Pricing {
    @Getter
    private final ContractLimits ContractLimits;
    @Getter
    private final Money Value;

    public Pricing(ContractLimits contractLimits, Money value) {
        ContractLimits = contractLimits;
        Value = value;
    }
}
