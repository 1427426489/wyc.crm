package com.dljd.crm.services;

import com.dljd.crm.beans.Type;

public interface TypeService extends BaseService<Type,String> {
    //查询字典类型是否重复
    boolean getCheckCode(String code);
}
