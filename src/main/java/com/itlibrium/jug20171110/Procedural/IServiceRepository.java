package com.itlibrium.jug20171110.Procedural;

public interface IServiceRepository
{
    Service Get(int id);
    void Save(Service service);
}
