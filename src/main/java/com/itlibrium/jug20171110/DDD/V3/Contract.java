package com.itlibrium.jug20171110.DDD.V3;

import com.itlibrium.jug20171110.BusinessException;

public class Contract implements IContract {
    private int _freeInterventionsLimit;
    private Money _sparePartsCostLimit;

    private int _freeInterventionsLimitUsed;
    private Money _sparePartsCostLimitUsed;

    public Contract(int freeInterventionsLimit, Money sparePartsCostLimit, int freeInterventionsLimitUsed, Money sparePartsCostLimitUsed) {
        _freeInterventionsLimit = freeInterventionsLimit;
        _sparePartsCostLimit = sparePartsCostLimit;
        _freeInterventionsLimitUsed = freeInterventionsLimitUsed;
        _sparePartsCostLimitUsed = sparePartsCostLimitUsed;
    }

    public ContractLimits GetContractLimits() {
        return ContractLimits.CreateInitial(
                FreeInterventionsLimit.CreateInitial(_freeInterventionsLimit - _freeInterventionsLimitUsed),
                SparePartsCostLimit.CreateInitial(Money.subtract(_sparePartsCostLimit, _sparePartsCostLimitUsed)));
    }

    public void AddUsage(ContractLimits contractLimits) throws BusinessException {
        if (contractLimits.getFreeInterventionsLimit().getUsed() > _freeInterventionsLimit - _freeInterventionsLimitUsed)
            throw new BusinessException("Brak darmowych interwencji do wykorzystania w ramach umowy serwisowej");

        if (contractLimits.getSparePartsCostLimit().getUsed().greaterThan(Money.subtract(_sparePartsCostLimit, _sparePartsCostLimitUsed)))
            throw new BusinessException("Koszt części zamiennych przekracza dopuszczalny limit w ramach umowy serwisowej");

        _freeInterventionsLimitUsed += contractLimits.getFreeInterventionsLimit().getUsed();
        _sparePartsCostLimitUsed = Money.sum(_sparePartsCostLimitUsed, contractLimits.getSparePartsCostLimit().getUsed());
    }
}
