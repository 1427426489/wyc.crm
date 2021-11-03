package com.dljd.crm.services;

import com.dljd.crm.beans.*;

import java.util.List;

public interface CustomerService extends BaseService<Customer,String> {

    Page getSome(Page page);

    boolean getExists(String name);

    List<CustomerRemark> getRemarks(String id);

    List<Contacts> getContacts(String id);

    List<Transaction> getTransactions(String id);
}
