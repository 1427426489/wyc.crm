package com.dljd.crm.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRemark {
    private String id;
    private String notePerson;
    private String noteContent;
    private String noteTime;
    private String editPerson;
    private String editTime;
    private int editFlag;
    private String customerId;
}
