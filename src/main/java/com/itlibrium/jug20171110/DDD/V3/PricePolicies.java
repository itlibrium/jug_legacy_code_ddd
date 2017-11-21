package com.itlibrium.jug20171110.DDD.V3;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class PricePolicies {
    public static PricePolicy labour(Money pricePerHour, Money minPrice,
                                     InterventionDuration freeInterventionTimeLimit) {
        return context ->
        {
            FreeInterventionsLimit freeInterventionsLimit =
                    context.getContractLimits().getFreeInterventionsLimit();
            if (freeInterventionsLimit.usedInCurrentIntervention()) {
                Money labourOverLimit =
                        labourOverLimit(context, pricePerHour, freeInterventionTimeLimit);
                return new Pricing(context.getContractLimits(), labourOverLimit);
            }

            if (freeInterventionsLimit.canUse()) {
                ContractLimits modifiedContractLimits =
                        context.getContractLimits().UseFreeIntervention();
                Money labourOverLimit =
                        labourOverLimit(context, pricePerHour, freeInterventionTimeLimit);
                return new Pricing(modifiedContractLimits, labourOverLimit);
            }

            Money labour = labour(context, pricePerHour, minPrice);
            return new Pricing(context.getContractLimits(), labour);
        };
    }

    private static Money labour(PricingContext context, Money pricePerHour, Money minPrice) {
        InterventionDuration duration = context.getServiceAction().getDuration();
        return Money.max(
                Money.multiply(pricePerHour, duration.getHours()),
                minPrice);
    }

    private static Money labourOverLimit(PricingContext context, Money pricePerHour,
                                         InterventionDuration freeInterventionTimeLimit) {
        InterventionDuration duration = context.getServiceAction().getDuration();
        InterventionDuration timeOverLimit = InterventionDuration.subtract(duration, freeInterventionTimeLimit);
        return Money.multiply(pricePerHour, timeOverLimit.getHours());
    }

    public static PricePolicy sparePartsCost(Map<Integer, Money> sparePartPrices) {
        return context ->
        {
            Money sparePartsCostLimit = context.getContractLimits().getSparePartsCostLimit().getAvailable();
            Money sparePartsCost = context.getServiceAction().getSparePartIds().stream()
                .map(sparePartPrices::get)
                .reduce(Money.ZERO, Money::sum);
            Money discount = Money.min(sparePartsCost, sparePartsCostLimit);

            ContractLimits modifiedContractLimits = context.getContractLimits().UseSparePartsCostLimit(discount);
            return new Pricing(
                modifiedContractLimits,
                Money.subtract(sparePartsCost, discount));
        };
    }

    public static PricePolicy free() {
        return fixed(Money.ZERO);
    }

    public static PricePolicy fixed(BigDecimal value) {
        return fixed(Money.fromDecimal(value));
    }

    public static PricePolicy fixed(Money value) {
        return context -> new Pricing(context.getContractLimits(), value);
    }

    public static PricePolicy sum(PricePolicy... policies) {
        return aggregate(Arrays.asList(policies), Money::sum);
    }

    public static PricePolicy aggregate(Collection<PricePolicy> policies, BiFunction<Money, Money, Money> valueAggregator) {
        return baseContext ->
        {
            Money total = Money.ZERO;
            PricingContext context = baseContext;
            for (PricePolicy policy : policies) {
                Pricing pricing = policy.apply(context);
                total = valueAggregator.apply(total, pricing.getValue());
                context = new PricingContext(context.getServiceAction(), pricing.getContractLimits());
            }
            return new Pricing(context.getContractLimits(), total);
        };
    }

    public static PricePolicy when(ServiceActionType[] types, PricePolicy policy) {
        return when(serviceAction -> Arrays.asList(types).contains(serviceAction.getType()), policy);
    }

    public static PricePolicy when(Predicate<ServiceAction> condition, PricePolicy policy) {
        return context -> {
            if (condition.test(context.getServiceAction()))
                return policy.apply(context);

            return new Pricing(context.getContractLimits(), Money.ZERO);
        };
    }
}
