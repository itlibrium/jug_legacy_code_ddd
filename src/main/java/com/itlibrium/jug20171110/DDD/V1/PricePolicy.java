package com.itlibrium.jug20171110.DDD.V1;

@FunctionalInterface
public interface PricePolicy {
    Money apply(ServiceAction serviceAction);
}
