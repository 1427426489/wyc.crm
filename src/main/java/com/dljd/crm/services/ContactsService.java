package com.dljd.crm.services;

import com.dljd.crm.beans.Contacts;

import java.util.List;

public interface ContactsService {
    List<String> getCustomerName(String name);

    int add(Contacts contacts);
}
