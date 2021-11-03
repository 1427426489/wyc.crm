package com.dljd.crm.services;

import com.dljd.crm.beans.*;
import com.dljd.crm.mapper.CustomerMapper;
import com.dljd.crm.util.LocalDateTimeUtil;
import com.dljd.crm.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Customer get(String id) {
        return customerMapper.get(id);
    }

    @Override
    public List<Customer> getAll() {
        return customerMapper.getAll();
    }

    @Override
    public int add(Customer param) {
        String createTime = LocalDateTimeUtil.localToStr(LocalDateTime.now());
        param.setId(UUIDUtil.getUUID());
        param.setCreateTime(createTime);
        return customerMapper.add(param);
    }

    @Override
    public int delete(String[] ids) {
        return customerMapper.delete(ids);
    }

    @Override
    public int update(Customer param) {
        String editTime = LocalDateTimeUtil.localToStr(LocalDateTime.now());
        param.setEditTime(editTime);
        return customerMapper.update(param);
    }


    @Override
    public Page getSome(Page page) {
        int currentPage = page.getCurrentPage();
        int rowsPerPage = page.getRowsPerPage();
        int beginRow = (currentPage - 1) * rowsPerPage;
        page.setData(customerMapper.getSome(beginRow, rowsPerPage, page.getSearchMap()));
        int totalRows = customerMapper.getCount();
        page.setTotalRows(totalRows);
        int totalPages = totalRows % rowsPerPage == 0 ? (totalRows / rowsPerPage) : (totalRows / rowsPerPage) + 1;
        page.setTotalPages(totalPages);
        return page;
    }

    @Override
    public boolean getExists(String name) {
        return customerMapper.getByName(name) != null;
    }

    @Override
    public List<CustomerRemark> getRemarks(String id) {
        return customerMapper.getRemarks(id);
    }

    @Override
    public List<Contacts> getContacts(String id) {
        return customerMapper.getContactsByCustomerId(id);
    }

    @Override
    public List<Transaction> getTransactions(String id) {
        return customerMapper.getTransactionsByCustomerId(id);
    }


}
