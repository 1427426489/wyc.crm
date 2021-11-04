package com.dljd.crm.mapper;

import com.dljd.crm.beans.ActRemark;
import com.dljd.crm.beans.Activity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ActivityMapper extends BaseMapper<Activity,String> {

    List<Activity> getSome(@Param("beginRow") int beginRow, @Param("rowsPerPage") int rowsPerPage, @Param("searchMap") Map<String,Object> searchMap);

    int getCount(@Param("searchMap") Map<String,Object> searchMap);

    int addAll(List<Activity> activities);

    Activity getRemark(String id);

    int addActRemark(ActRemark actRemark);

    int updateActRemark(ActRemark actRemark);

    int deleteActRemark(String id);

    List<Activity> getLikeByName(String actName);

    List<Map<String,Object>> getStageCount();
}
