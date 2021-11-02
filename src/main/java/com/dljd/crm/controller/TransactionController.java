package com.dljd.crm.controller;

import com.dljd.crm.beans.Page;
import com.dljd.crm.services.TransactionService;
import com.dljd.crm.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/tran")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TypeService typeService;

    @RequestMapping("/indexView")
    public String indexView(Model model){
        addTypeListToModel(model);
        return "/workbench/transaction/index";
    }

    @RequestMapping("/getPage.json")
    @ResponseBody
    public Page getPage(Page page){
        return transactionService.getSome(page);
    }

    @RequestMapping("/saveView")
    public String saveView(Model model){
        addTypeListToModel(model);
        model.addAttribute("stage2possiMap",transactionService.getStage2possiMap());
        return "workbench/transaction/save";
    }


    private void addTypeListToModel(Model model){
        model.addAttribute("stageList",typeService.get("stage").getValues());
        model.addAttribute("transactionTypeList",typeService.get("transaction").getValues());
        model.addAttribute("sourceList",typeService.get("source").getValues());
    }

}
