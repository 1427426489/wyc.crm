package com.dljd.crm.mapper;

import com.dljd.crm.beans.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptMapper extends BaseMapper<Dept,String> {

    List<Dept> getSome(@Param("beginRow") int beginRow,@Param("rowsPerPage") int rowsPerPage);

    int getCount();

    Dept getByNo(String no);
}
