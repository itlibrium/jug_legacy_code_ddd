package com.itlibrium.jug20171110.Procedural;

public interface IServiceContractRepository
{
    Contract Get(int id);
    void Save(Contract contract);
}
