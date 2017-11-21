package com.itlibrium.jug20171110.DDD.V3;

import lombok.Getter;

public class SparePartsCostLimit {
    @Getter
    private final Money initial;
    @Getter
    private final Money used;

    public Money getAvailable() {
        return Money.subtract(initial, used);
    }

    public static SparePartsCostLimit CreateInitial(Money value) {
        return new SparePartsCostLimit(value);
    }

    private SparePartsCostLimit(Money initial) {
        this.initial = initial;
        this.used = Money.ZERO;
    }

    public SparePartsCostLimit Use(Money value) {
        return new SparePartsCostLimit(initial, Money.sum(used, value));
    }

    private SparePartsCostLimit(Money initial, Money used) {
        this.initial = initial;
        this.used = used;
    }
}
