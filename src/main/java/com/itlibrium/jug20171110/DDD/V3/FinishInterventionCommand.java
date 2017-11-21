package com.itlibrium.jug20171110.DDD.V3;

import lombok.Getter;

import java.util.Collection;

public class FinishInterventionCommand {
    @Getter
    private final int interventionId;
    @Getter
    private final Collection<ServiceAction> serviceActions;

    public FinishInterventionCommand(int interventionId, Collection<ServiceAction> serviceActions) {
        this.interventionId = interventionId;
        this.serviceActions = serviceActions;
    }
}
