package com.itlibrium.jug20171110.Procedural;

import com.itlibrium.jug20171110.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Service
{
    private int id;
    private Client client;
    private Engineer engineer;
    private ServiceStatus status;
    private int timeSlotId;
    private double duration;
    private List<SparePart> spareParts;
    private BigDecimal price;

    private boolean warranty;
}
