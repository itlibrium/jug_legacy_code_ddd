package com.itlibrium.jug20171110.DDD.V1;

public interface IPricePolicyFactory {
    PricePolicy createFor(Intervention intervention);
}
