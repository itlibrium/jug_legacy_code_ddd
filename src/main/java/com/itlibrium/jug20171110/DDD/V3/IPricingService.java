package com.itlibrium.jug20171110.DDD.V3;

import java.util.Collection;

public interface IPricingService {
    InterventionPricing GetPricingFor(Intervention intervention, Collection<ServiceAction> serviceActions, ContractLimits contractLimits);
}
