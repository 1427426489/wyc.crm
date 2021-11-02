package com.dljd.crm.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Value implements Serializable {
    private String id;
    private String value;
    private String text;
    private int orderNo;
    private String typeCode;
    private Type type;

}
