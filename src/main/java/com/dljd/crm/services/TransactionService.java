package com.dljd.crm.services;

import com.dljd.crm.beans.Page;
import com.dljd.crm.beans.TransHistory;
import com.dljd.crm.beans.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    Page getSome(Page page);

    Transaction get(String id);

    int add(Transaction transaction);

    List<TransHistory> getHistory(String id);

    int changeStage(String id,String stage,String editBy);

    //阶段和可能性的对应关系
    Map<String,String> getStage2possiMap();
}
