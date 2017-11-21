package com.itlibrium.jug20171110.DDD.V2;

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
        PricingCategory getPricingCategoryForClient(int clientId);

        EquipmentModel GetEquipmentModelForClient(int clientId);
    }

    public interface ISparePartsFacade {
        Map<Integer, Money> getPrices();
    }

    public PricePolicy createFor(Intervention intervention) {
        PricingCategory pricingCategory =
                crmFacade.getPricingCategoryForClient(intervention.getClientId());
        Map<Integer, Money> sparePartPrices = sparePartsFacade.getPrices();

        return PricePolicies.sum(
            PricePolicies.when(
                serviceAction ->
                    serviceAction.getType() == ServiceActionType.Repair ||
                    serviceAction.getType() == ServiceActionType.Review,
                PricePolicies.sum(
                    PricePolicies.labour(
                        Money.fromDecimal(pricingCategory.getPricePerHour()),
                        Money.fromDecimal(pricingCategory.getMinPrice())),
                    PricePolicies.sparePartsCost(sparePartPrices))),
            PricePolicies.when(
                serviceAction ->
                    serviceAction.getType() == ServiceActionType.WarrantyRepair ||
                    serviceAction.getType() == ServiceActionType.WarrantyReview,
                PricePolicies.free()));
    }
}