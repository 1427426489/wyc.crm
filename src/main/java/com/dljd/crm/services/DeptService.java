package com.dljd.crm.services;

import com.dljd.crm.beans.Dept;
import com.dljd.crm.beans.Page;

public interface DeptService extends BaseService<Dept,String> {
    Page getSome(Page page);

    boolean getExists(String no);
}
