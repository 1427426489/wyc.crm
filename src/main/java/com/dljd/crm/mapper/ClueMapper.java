package com.dljd.crm.mapper;

import com.dljd.crm.beans.Activity;
import com.dljd.crm.beans.Clue;
import com.dljd.crm.beans.ClueActivity;
import com.dljd.crm.beans.ClueRemark;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ClueMapper extends BaseMapper<Clue,String> {

    List<Clue> getSome(@Param("beginRow") int beginRow, @Param("rowsPerPage") int rowsPerPage, @Param("searchMap") Map<String,Object> searchMap);

    int getCount(@Param("searchMap") Map<String,Object> searchMap);

    int addAll(List<Clue> clues);

    List<ClueRemark> getRemarks(String id);

    List<Activity> getActivities(String id);

    int addRemark(ClueRemark clueRemark);

    int updateRemark(ClueRemark clueRemark);

    int deleteRemark(String id);

    int deleteClueActRelation(@Param("clueId") String clueId,@Param("actId") String actId);

    int deleteByClueId(String clueId);

    int addClueActivity(ClueActivity clueActivity);

    int deleteRemarkByClueId(String clueId);
}
