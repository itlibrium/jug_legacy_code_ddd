package com.itlibrium.jug20171110.DDD.V2;

public interface IPricePolicyFactory {
    PricePolicy createFor(Intervention intervention);
}
