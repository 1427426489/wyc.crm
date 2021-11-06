package com.dljd.crm.services;

import com.dljd.crm.beans.*;
import com.dljd.crm.mapper.CustomerMapper;
import com.dljd.crm.mapper.TransactionMapper;
import com.dljd.crm.mapper.TypeMapper;
import com.dljd.crm.mapper.ValueMapper;
import com.dljd.crm.util.LocalDateTimeUtil;
import com.dljd.crm.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ValueMapper valueMapper;

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

    @Override
    public Transaction get(String id) {
        return transactionMapper.get(id);
    }

    @Override
    public int add(Transaction transaction) {
        transaction.setId(UUIDUtil.getUUID());
        String createTime = LocalDateTimeUtil.localToStr(LocalDateTime.now());
        transaction.setCreateTime(createTime);
        //判断客户是否存在，不存在则新建
        String customerId = customerMapper.getIdByName(transaction.getCustomerName());
        //存在
        if (customerId != null && !"".equals(customerId)) {
            transaction.setCustomerId(customerId);
            return transactionMapper.add(transaction);
        }
        //不存在
        Customer customer = new Customer();
        customerId = UUIDUtil.getUUID();
        customer.setId(customerId);
        customer.setOwner(transaction.getOwner());
        customer.setName(transaction.getCustomerName());
        customer.setCreateBy(transaction.getCreateBy());
        customer.setCreateTime(createTime);
        customerMapper.add(customer);
        transaction.setCustomerId(customerId);
        return transactionMapper.add(transaction);
    }

    @Override
    public List<TransHistory> getHistory(String id) {
        return transactionMapper.getHistory(id);
    }

    @Override
    public int changeStage(String id, String stage, String editBy) {
        String nowTime = LocalDateTimeUtil.localToStr(LocalDateTime.now());
        //添加阶段历史
        Transaction transaction = transactionMapper.get(id);
        TransHistory transHistory = new TransHistory();
        transHistory.setId(UUIDUtil.getUUID());
        transHistory.setStage(transaction.getStage());
        transHistory.setAmountOfMoney(transaction.getAmountOfMoney());
        transHistory.setExpectedClosingDate(transaction.getExpectedClosingDate());
        transHistory.setEditBy(editBy);
        transHistory.setEditTime(nowTime);
        transHistory.setTransactionId(id);
        int count = transactionMapper.addHistory(transHistory);
        //修改交易阶段
        count+=transactionMapper.updateStage(id, stage, editBy, nowTime);
        return count;
    }

    //获取阶段和可能性对应关系的Map集合
    @Override
    public Map<String, String> getStage2possiMap() {
        Map<String, String> stage2possiMap = new HashMap<>();
        List<Value> stages = typeMapper.get("stage").getValues();
        for (Value stage : stages) {
            stage2possiMap.put(stage.getValue(), Integer.toString(stage.getOrderNo() * 10));
        }
        return stage2possiMap;
    }

    @Override
    public List<Map<String, Object>> getStageCount() {
        return transactionMapper.getStageCount();
    }
}
