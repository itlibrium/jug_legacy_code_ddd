package com.itlibrium.jug20171110.DDD.V3;

import com.itlibrium.jug20171110.EquipmentModel;
import com.itlibrium.jug20171110.PricingCategory;

import java.util.Map;

public class PricePolicyFactory implements IPricePolicyFactory {
    private final ICrmFacade crmFacade;
    private final ISparePartsFacade sparePartsFacade;

    public PricePolicyFactory(ICrmFacade crmFacade, ISparePartsFacade sparePartsFacade) {
        this.crmFacade = crmFacade;
        this.sparePartsFacade = sparePartsFacade;
    }

    public interface ICrmFacade {
        PricingCategory GetPricingCategoryForClient(int clientId);

        EquipmentModel GetEquipmentModelForClient(int clientId);
    }

    public interface ISparePartsFacade {
        Map<Integer, Money> GetPrices();
    }

    public PricePolicy CreateFor(Intervention intervention) {
        PricingCategory pricingCategory =
                crmFacade.GetPricingCategoryForClient(intervention.getClientId());
        Map<Integer, Money> sparePartPrices = sparePartsFacade.GetPrices();
        EquipmentModel equipmentModel =
                crmFacade.GetEquipmentModelForClient(intervention.getClientId());

        return PricePolicies.sum(
            PricePolicies.when(
                serviceAction ->
                    serviceAction.getType() == ServiceActionType.Repair ||
                    serviceAction.getType() == ServiceActionType.Review,
                PricePolicies.sum(
                    PricePolicies.labour(
                        Money.fromDecimal(pricingCategory.getPricePerHour()),
                        Money.fromDecimal(pricingCategory.getMinPrice()),
                        InterventionDuration.FromHours(
                                equipmentModel.getFreeInterventionTimeLimit())),
                    PricePolicies.sparePartsCost(sparePartPrices))),
                PricePolicies.when(
                    serviceAction ->
                        serviceAction.getType() == ServiceActionType.WarrantyRepair ||
                        serviceAction.getType() == ServiceActionType.WarrantyReview,
                    PricePolicies.free()));
    }
}