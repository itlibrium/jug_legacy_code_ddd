package com.itlibrium.jug20171110.DDD.V2;

public interface IInterventionRepository {
    Intervention get(int id);
    void save(Intervention intervention);
}
