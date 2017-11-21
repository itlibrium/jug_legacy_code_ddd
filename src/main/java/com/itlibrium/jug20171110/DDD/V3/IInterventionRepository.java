package com.itlibrium.jug20171110.DDD.V3;

public interface IInterventionRepository {
    Intervention Get(int id);
    void Save(Intervention intervention);
}
