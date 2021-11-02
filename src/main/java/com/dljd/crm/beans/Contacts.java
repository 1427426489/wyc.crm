package com.dljd.crm.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contacts {
    private String id;
    private String owner;
    private String source;
    private String appellation;
    private String fullName;
    private String email;
    private String job;
    private String mphone;
    private String description;
    private String birth;
    private String customerId;
    private String createBy;
    private String createTime;
    private String editBy;
    private String editTime;
    private String contactSummary;
    private String nextContactTime;
    private String address;
    private Customer customer;
}
