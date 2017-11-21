package com.itlibrium.jug20171110.DDD.V2;

@FunctionalInterface
public interface PricePolicy {
    Money apply(ServiceAction serviceAction);
}
