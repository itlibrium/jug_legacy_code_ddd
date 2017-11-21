package com.itlibrium.jug20171110.DDD.V3;

@FunctionalInterface
public interface PricePolicy {
    Pricing apply(PricingContext context);
}
