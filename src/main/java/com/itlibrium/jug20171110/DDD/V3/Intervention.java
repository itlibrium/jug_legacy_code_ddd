package com.itlibrium.jug20171110.DDD.V3;

import com.itlibrium.jug20171110.BusinessException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Intervention {
    private final int id;
    @Getter
    private final int clientId;
    @Getter
    private final int engineerId;
    private final List<ServiceActionType> serviceActionTypes;

    private List<ServiceAction> serviceActions;
    private Money price;

    public static Intervention CreateFor(int clientId, int engineerId, Collection<ServiceActionType> serviceActionTypes) {
        return new Intervention(clientId, engineerId, serviceActionTypes);
    }

    private Intervention(int clientId, int engineerId, Collection<ServiceActionType> serviceActionTypes) {
        id = 0;
        this.clientId = clientId;
        this.engineerId = engineerId;
        this.serviceActionTypes = new ArrayList<>(serviceActionTypes);
    }

    public Intervention(int id, int clientId, int engineerId, List<ServiceActionType> serviceActionTypes,
                        Collection<ServiceAction> serviceActions) {
        this.id = id;
        this.clientId = clientId;
        this.engineerId = engineerId;
        this.serviceActionTypes = serviceActionTypes;
        this.serviceActions = new ArrayList<>(serviceActions);
    }

    public void Finish(Collection<ServiceAction> serviceActions,
                       InterventionPricing interventionPricing)
            throws BusinessException {

        if (this.serviceActions != null)
            throw new BusinessException("Nie można zakończyć interwencji więcej niż raz");

        this.serviceActions = new ArrayList<>(serviceActions);
        price = interventionPricing.getTotalPrice();
    }
}
