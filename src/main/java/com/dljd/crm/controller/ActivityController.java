package com.dljd.crm.controller;

import com.dljd.crm.beans.ActRemark;
import com.dljd.crm.beans.Activity;
import com.dljd.crm.beans.Page;
import com.dljd.crm.beans.User;
import com.dljd.crm.services.ActivityService;
import com.dljd.crm.util.LocalDateTimeUtil;
import com.dljd.crm.util.UUIDUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/act")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpSession session;

    @RequestMapping("/indexView")
    public String indexView() {
        return "workbench/activity/index";
    }

    @RequestMapping("/get.json")
    @ResponseBody
    public Object get(String id) {
        return activityService.get(id);
    }

    @RequestMapping("/getAll.json")
    @ResponseBody
    public Object getAllJson(Page page) {
        return activityService.getSome(page);
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Map<String, Object> saveDo(Activity activity) {
        activityService.add(activity);
        return new HashMap<String, Object>() {{
            put("success", true);
        }};
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public Map<String, Object> deleteDo(String[] ids) {
        activityService.delete(ids);
        return new HashMap<String, Object>() {{
            put("success", true);
        }};
    }

    @RequestMapping("/update.do")
    @ResponseBody
    public Map<String, Object> update(Activity activity) {
        activityService.update(activity);
        return new HashMap<String, Object>() {{
            put("success", true);
        }};
    }

    //??????????????????excel??????
    @RequestMapping("/export.do")
    public String exportDo() throws IOException {
        Workbook workbook = activityService.actExport();
        //7.????????????????????????excel???????????????????????????????????????????????????????????????
        ServletOutputStream outputStream = response.getOutputStream();
        //???????????????
        response.reset();
        //?????????????????????????????????????????????????????????
        response.setHeader("Content-disposition", "attachment;filename=activities.xls");
        //??????????????????????????????Excel??????
        response.setContentType("application/msexcel");
        workbook.write(outputStream);
        outputStream.close();
        return "redirect:/act/indexView";
    }

    @RequestMapping("/import.do")
    @ResponseBody
    public Object importDo(MultipartFile file) throws IOException {
        activityService.actImport(file);
        return new HashMap<String, Object>() {{
            put("success", true);
        }};
    }

    @RequestMapping("/detailView")
    public String detailView(){
        return "workbench/activity/detail";
    }

    @RequestMapping("/getRemark.json")
    @ResponseBody
    public Activity getRemark(String id){
        return activityService.getRemark(id);
    }

    @RequestMapping("/saveRemark.do")
    @ResponseBody
    public Object saveRemarkDo(ActRemark actRemark){
        //id??????????????????????????????????????????????????????
        String id = actRemark.getId();
        if (id == null || "".equals(id)){
            //????????????
            activityService.addActRemark(actRemark);
        }else {
            //????????????
            activityService.updateActRemark(actRemark);
        }
        return new HashMap<String, Object>() {{
            put("success", true);
        }};
    }

    @RequestMapping("delRemark.do")
    @ResponseBody
    public Object delRemarkDo(String id){
        activityService.deleteActRemark(id);
        return new HashMap<String, Object>() {{
            put("success", true);
        }};
    }

    @RequestMapping("/getAll2.json")
    @ResponseBody
    public Object getAll2(String actName){
        List<Activity> activities = null;
        //??????????????????????????????actName??????null??????????????????
        if (actName==null){
            activities = activityService.getAll();
        }else {
            activities = activityService.getLikeByName(actName);
        }
        return activities;
    }

    @RequestMapping("/getStageCount.json")
    @ResponseBody
    public List<Map<String,Object>> getStageCount(){
        return activityService.getStageCount();
    }
}

