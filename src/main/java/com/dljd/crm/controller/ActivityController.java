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

    //将数据导出成excel文件
    @RequestMapping("/export.do")
    public String exportDo() throws IOException {
        Workbook workbook = activityService.actExport();
        //7.创建一个文件保存excel内容并通过响应对象的输出流输出到系统文件中
        ServletOutputStream outputStream = response.getOutputStream();
        //清除空白行
        response.reset();
        //设置响应头信息，告诉浏览器这是下载文件
        response.setHeader("Content-disposition", "attachment;filename=activities.xls");
        //设置响应的输出格式为Excel格式
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
        //id为空表示添加操作，不为空表示修改操作
        String id = actRemark.getId();
        if (id == null || "".equals(id)){
            //添加备注
            activityService.addActRemark(actRemark);
        }else {
            //修改备注
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
        //查询所有市场活动，若actName不为null，为模糊查询
        if (actName==null){
            activities = activityService.getAll();
        }else {
            activities = activityService.getLikeByName(actName);
        }
        return activities;
    }
}

