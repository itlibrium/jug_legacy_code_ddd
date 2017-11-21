package com.itlibrium.jug20171110;

import lombok.Data;

@Data
public class Client
{
    private int Id;
    private String Name;
    private EquipmentModel EquipmentModel;
    private com.itlibrium.jug20171110.Procedural.Contract Contract;
}
