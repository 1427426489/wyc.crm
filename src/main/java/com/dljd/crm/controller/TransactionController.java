package com.dljd.crm.controller;

import com.dljd.crm.beans.Page;
import com.dljd.crm.beans.TransHistory;
import com.dljd.crm.beans.Transaction;
import com.dljd.crm.beans.User;
import com.dljd.crm.services.TransactionService;
import com.dljd.crm.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/tran")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private HttpSession session;

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
        //todo 添加阶段和可能性对应关系有问题
//        model.addAttribute("stage2possiMap",transactionService.getStage2possiMap());
        return "workbench/transaction/save";
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Object saveDo(Transaction transaction){
        User user = (User) session.getAttribute("user");
        transaction.setCreateBy(user.getName());
        transactionService.add(transaction);
        return new HashMap<String,Object>(){{
            put("success",true);
        }};
    }


    private void addTypeListToModel(Model model){
        model.addAttribute("stageList",typeService.get("stage").getValues());
        model.addAttribute("transactionTypeList",typeService.get("transaction").getValues());
        model.addAttribute("sourceList",typeService.get("source").getValues());
    }

    @RequestMapping("/detailView")
    public String detailView(){
        return "workbench/transaction/detail";
    }

    @RequestMapping("/get.json")
    @ResponseBody
    public Transaction get(String id){
        return transactionService.get(id);
    }

    @RequestMapping("/getHistory.json")
    @ResponseBody
    public List<TransHistory> getHistory(String id){
        return transactionService.getHistory(id);
    }

}
