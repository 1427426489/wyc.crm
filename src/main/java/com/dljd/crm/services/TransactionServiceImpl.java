package com.dljd.crm.services;

import com.dljd.crm.beans.Page;
import com.dljd.crm.beans.Transaction;
import com.dljd.crm.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

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
}
