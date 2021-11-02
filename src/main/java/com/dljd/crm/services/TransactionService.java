package com.dljd.crm.services;

import com.dljd.crm.beans.Page;

import java.util.Map;

public interface TransactionService {
    Page getSome(Page page);

    //阶段和可能性的对应关系
    Map<String,String> getStage2possiMap();
}
