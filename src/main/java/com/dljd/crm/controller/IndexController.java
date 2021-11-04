package com.dljd.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/workbench/indexView")
    public String workIndexView(){
        return "workbench/index";
    }

    @RequestMapping("/workbench/main/indexView")
    public String mainIndexView(){
        return "/workbench/main/index";
    }

    @RequestMapping("/settings/indexView")
    public String settingsIndexView(){
        return "settings/index";
    }

    @RequestMapping("/settings/dictionary/indexView")
    public String dictionaryIndexView(){
        return "/settings/dictionary/index";
    }

    @RequestMapping("/chart/transaction/indexView")
    public String transactionChart(){
        return "workbench/chart/transaction/index";
    }
}
