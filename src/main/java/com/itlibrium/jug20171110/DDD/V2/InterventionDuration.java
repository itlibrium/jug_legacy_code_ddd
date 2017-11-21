package com.itlibrium.jug20171110.DDD.V2;

import java.time.Duration;

public class InterventionDuration {

    public static InterventionDuration FromHours(int hours) {
        return new InterventionDuration(Duration.ofHours(hours));
    }

    public static InterventionDuration subtract(InterventionDuration duration1, InterventionDuration duration2){
        if(duration1._value.compareTo(duration2._value) == -1)
            return new InterventionDuration(Duration.ZERO);

        return new InterventionDuration(duration1._value.minus(duration1._value));
    }

    private Duration _value;

    public double getHours() {
        return _value.toMinutes() / 60;
    }

    private InterventionDuration(Duration value) {
        if (value.getSeconds() < 0) throw new IllegalArgumentException();
        _value = value;
    }
}
