package com.dljd.crm.services;

import com.dljd.crm.beans.User;

import java.util.List;

public interface LoginService {

    User login(String username,String password);

    List<String> getAllUsername();
}
