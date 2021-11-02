package com.dljd.crm.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransHistory {
    private String id;
    private String stage;
    private Long amountOfMoney;
    private String expectedClosingDate;
    private String editTime;
    private String editBy;
    private String transactionId;
}
