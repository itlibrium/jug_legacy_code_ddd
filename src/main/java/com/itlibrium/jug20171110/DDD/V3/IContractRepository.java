package com.itlibrium.jug20171110.DDD.V3;

public interface IContractRepository {
    IContract GetForClient(int id);

    void Save(IContract contract);
}
