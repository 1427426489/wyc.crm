package com.dljd.crm.services;

import com.dljd.crm.beans.ActRemark;
import com.dljd.crm.beans.Activity;
import com.dljd.crm.beans.Page;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ActivityService extends BaseService<Activity,String> {
    Page getSome(Page page);

    Activity getRemark(String id);

    Workbook actExport();

    void actImport(MultipartFile file) throws IOException;

    int addActRemark(ActRemark actRemark);

    int updateActRemark(ActRemark actRemark);

    int deleteActRemark(String id);

    List<Activity> getLikeByName(String actName);

    List<Map<String,Object>> getStageCount();
}
