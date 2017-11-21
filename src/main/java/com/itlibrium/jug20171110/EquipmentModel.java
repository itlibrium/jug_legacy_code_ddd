package com.itlibrium.jug20171110;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class EquipmentModel
{
    private int id;
    private String name;
    private PricingCategory pricingCategory;
    private List<LicenseType> requiredLicenses;
    private BigDecimal warrantyReviewPrice;
    private int freeInterventionTimeLimit;
}
