package com.dljd.crm.services;

import com.dljd.crm.beans.Page;
import com.dljd.crm.beans.Transaction;
import com.dljd.crm.beans.Value;
import com.dljd.crm.mapper.TransactionMapper;
import com.dljd.crm.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Page getSome(Page page) {
        int rowsPerPage = page.getRowsPerPage();
        int beginRow = (page.getCurrentPage() - 1) * rowsPerPage;
        List<Transaction> transactions = transactionMapper.getSome(beginRow, rowsPerPage, page.getSearchMap());
        page.setData(transactions);
        int totalRows = transactionMapper.getCount(page.getSearchMap());
        page.setTotalRows(totalRows);
        int totalPages = totalRows % rowsPerPage == 0 ? (totalRows / rowsPerPage) : (totalRows / rowsPerPage) + 1;
        page.setTotalPages(totalPages);
        return page;
    }

    //获取阶段和可能性对应关系的Map集合
    @Override
    public Map<String, String> getStage2possiMap() {
        Map<String,String> stage2possiMap = new HashMap<>();
        List<Value> stages = typeMapper.get("stage").getValues();
        for (Value stage : stages) {
            stage2possiMap.put(stage.getValue(),"10%");
        }
        return stage2possiMap;
    }
}
