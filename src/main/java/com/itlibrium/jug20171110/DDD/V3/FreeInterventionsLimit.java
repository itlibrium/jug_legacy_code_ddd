package com.itlibrium.jug20171110.DDD.V3;

import lombok.Getter;

public class FreeInterventionsLimit {
    @Getter
    private final int initial;
    @Getter
    private final int used;

    public boolean usedInCurrentIntervention() {return used > 0;}
    public boolean canUse() {return used < initial;}

    public static FreeInterventionsLimit CreateInitial(int initial) {
        return new FreeInterventionsLimit(initial);}

    private FreeInterventionsLimit(int initial) {
        this.initial = initial;
        used = 0;
    }

    public FreeInterventionsLimit Use() {
        return new FreeInterventionsLimit(initial, used + 1);
    }

    private FreeInterventionsLimit(int initial, int used) {
        this.initial = initial;
        this.used = used;
    }
}
