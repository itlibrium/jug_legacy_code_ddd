package com.itlibrium.jug20171110.DDD.V3;

import com.itlibrium.jug20171110.BusinessException;

public interface IContract
{
    ContractLimits GetContractLimits();
    void AddUsage(ContractLimits interventionPricingContractLimits) throws BusinessException;
}
