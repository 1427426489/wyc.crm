package com.dljd.crm.mapper;

import com.dljd.crm.beans.Customer;
import com.dljd.crm.beans.CustomerRemark;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustomerMapper extends BaseMapper<Customer,String> {
    List<Customer> getSome(@Param("beginRow") int beginRow, @Param("rowsPerPage") int rowsPerPage, @Param("searchMap") Map<String,Object> searchMap);

    int getCount();

    Customer getByName(String name);

    List<CustomerRemark> getRemarks(String id);

    String getIdByName(String name);
}
