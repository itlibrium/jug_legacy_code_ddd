package com.itlibrium.jug20171110.DDD.V3;

import java.util.Collection;

public class PricingService implements IPricingService {
    private IPricePolicyFactory pricePolicyFactory;

    public PricingService(IPricePolicyFactory pricePolicyFactory) {
        this.pricePolicyFactory = pricePolicyFactory;
    }

    public InterventionPricing GetPricingFor(Intervention intervention,
                                             Collection<ServiceAction> serviceActions,
                                             ContractLimits contractLimits) {

        PricePolicy pricePolicy = pricePolicyFactory.CreateFor(intervention);
        Money totalPrice = Money.ZERO;
        for (ServiceAction serviceAction : serviceActions) {
            PricingContext context = new PricingContext(serviceAction, contractLimits);
            Pricing pricing = pricePolicy.apply(context);
            contractLimits = pricing.getContractLimits();
            totalPrice = Money.sum(totalPrice, pricing.getValue());
        }
        return new InterventionPricing(totalPrice, contractLimits);
    }
}
