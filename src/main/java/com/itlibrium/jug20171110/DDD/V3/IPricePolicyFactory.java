package com.itlibrium.jug20171110.DDD.V3;

public interface IPricePolicyFactory {
    PricePolicy CreateFor(Intervention intervention);
}
