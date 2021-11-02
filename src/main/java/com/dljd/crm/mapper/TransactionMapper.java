package com.dljd.crm.mapper;

import com.dljd.crm.beans.Transaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TransactionMapper {

    List<Transaction> getSome(@Param("beginRow") int beginRow, @Param("rowsPerPage") int rowsPerPage,@Param("searchMap") Map searchMap);

    int getCount(@Param("searchMap") Map searchMap);
}
