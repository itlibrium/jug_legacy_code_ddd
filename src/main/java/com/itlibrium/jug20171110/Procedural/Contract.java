package com.itlibrium.jug20171110.Procedural;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Contract
{
    private int id;
    private Date expirationDate;
    private int freeInterventions;
    private int freeInterventionsUsed;
    private BigDecimal sparePartsCostLimit;
    private BigDecimal sparePartsCostLimitUsed;
}
