package com.dljd.crm.services;

import com.dljd.crm.beans.Contacts;
import com.dljd.crm.mapper.ContactsMapper;
import com.dljd.crm.mapper.CustomerMapper;
import com.dljd.crm.util.LocalDateTimeUtil;
import com.dljd.crm.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactsServiceImpl implements ContactsService {

    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<String> getCustomerName(String name) {
        return contactsMapper.getCustomerName(name);
    }

    @Override
    public int add(Contacts contacts) {
        contacts.setId(UUIDUtil.getUUID());
        String createTime = LocalDateTimeUtil.localToStr(LocalDateTime.now());
        contacts.setCreateTime(createTime);
        contacts.setCustomerId(customerMapper.getIdByName(contacts.getCustomerId()));
        //转换生日
        String birth = contacts.getBirth().substring(0, 10);
        contacts.setBirth(birth);
        return contactsMapper.add(contacts);
    }

    @Override
    public List<Contacts> getByName(String name) {
        return contactsMapper.getByName(name);
    }
}
