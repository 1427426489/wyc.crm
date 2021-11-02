package com.dljd.crm.mapper;

import com.dljd.crm.beans.Contacts;

import java.util.List;

public interface ContactsMapper{

    List<String> getCustomerName(String name);

    int add(Contacts contacts);

    List<Contacts> getByName(String name);
}
