package com.dljd.crm.services;

import com.dljd.crm.beans.Activity;
import com.dljd.crm.beans.Clue;
import com.dljd.crm.beans.ClueRemark;
import com.dljd.crm.beans.Page;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ClueService extends BaseService<Clue,String> {

    Page getSome(Page page);

    void importDo(MultipartFile file) throws IOException;

    Workbook exportDo(String[] fields);

    List<ClueRemark> getRemarks(String id);

    List<Activity> getActivities(String id);

    int addRemark(ClueRemark clueRemark);

    int updateRemark(ClueRemark clueRemark);

    int deleteRemark(String id);

    //解除线索与市场活动的关联
    int unbind(String clueId,String actId);

    //关联市场活动
    int bind(String clueId,String[] actIds);
}
