package com.itlibrium.jug20171110.DDD.V3;

import lombok.Getter;

public class ContractLimits {
    @Getter
    private final FreeInterventionsLimit freeInterventionsLimit;
    @Getter
    private final SparePartsCostLimit sparePartsCostLimit;

    public static ContractLimits NoContract(){
        return new ContractLimits(FreeInterventionsLimit.CreateInitial(0), SparePartsCostLimit.CreateInitial(Money.ZERO));
    }

    public static ContractLimits CreateInitial(FreeInterventionsLimit freeInterventionsLimit, SparePartsCostLimit sparePartsCostLimit){
        return new ContractLimits(freeInterventionsLimit, sparePartsCostLimit);
    }

    private ContractLimits(FreeInterventionsLimit freeInterventionsLimit, SparePartsCostLimit sparePartsCostLimit) {
        this.freeInterventionsLimit = freeInterventionsLimit;
        this.sparePartsCostLimit = sparePartsCostLimit;
    }

    public ContractLimits UseFreeIntervention() {
        return new ContractLimits(freeInterventionsLimit.Use(), sparePartsCostLimit);
    }

    public ContractLimits UseSparePartsCostLimit(Money value) {
        return new ContractLimits(freeInterventionsLimit, sparePartsCostLimit.Use(value));
    }
}
