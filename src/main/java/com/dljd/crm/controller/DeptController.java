package com.dljd.crm.controller;

import com.dljd.crm.beans.Dept;
import com.dljd.crm.beans.Page;
import com.dljd.crm.services.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @RequestMapping("/indexView")
    public String indexView(){
        return "settings/dept/index";
    }

    @RequestMapping("/getAll.json")
    @ResponseBody
    public Page getAll(Page page){
        return deptService.getSome(page);
    }

    @RequestMapping("/getExists.json")
    @ResponseBody
    public boolean getExistsJson(String no){
        return deptService.getExists(no);
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Map<String,Object> saveDo(Dept dept){
        deptService.add(dept);
        return new HashMap<String, Object>(){{
            put("success",true);
        }};
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public Map<String,Object> deleteDo(String[] ids){
        deptService.delete(ids);
        return new HashMap<String, Object>(){{
            put("success",true);
        }};
    }

    @RequestMapping("/get.json")
    @ResponseBody
    public Dept getJson(String id){
        return deptService.get(id);
    }

    @RequestMapping("/update.do")
    @ResponseBody
    public Map<String,Object> updateDo(Dept dept){
        deptService.update(dept);
        return new HashMap<String, Object>(){{
            put("success",true);
        }};
    }
}
