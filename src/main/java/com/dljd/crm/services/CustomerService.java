package com.dljd.crm.services;

import com.dljd.crm.beans.Customer;
import com.dljd.crm.beans.CustomerRemark;
import com.dljd.crm.beans.Page;

import java.util.List;

public interface CustomerService extends BaseService<Customer,String> {

    Page getSome(Page page);

    boolean getExists(String name);

    List<CustomerRemark> getRemarks(String id);
}
