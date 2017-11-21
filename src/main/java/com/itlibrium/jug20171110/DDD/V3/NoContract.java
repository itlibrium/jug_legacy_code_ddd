package com.itlibrium.jug20171110.DDD.V3;

public class NoContract implements IContract
    {
        public ContractLimits GetContractLimits() {
            return ContractLimits.NoContract();
        }
        public void AddUsage(ContractLimits contractLimits) { }
    }
