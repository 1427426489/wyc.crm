package com.dljd.crm.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clue implements Serializable {
    private String id;
    private String owner;
    private String company;
    private String phone;
    private String website;
    private String description;
    private String fullName;
    private String appellation;
    private String source;
    private String email;
    private String mphone;
    private String job;
    private String state;
    private String createBy;
    private String createTime;
    private String editBy;
    private String editTime;
    private String contactSummary;
    private String nextContactTime;
    private String address;

}
