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
import java.util.Map;

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
        model.addAttribute("stage2possiMap",transactionService.getStage2possiMap());
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
    public String detailView(Model model){
        model.addAttribute("stage2possiMap",transactionService.getStage2possiMap());
        model.addAttribute("stageList",typeService.get("stage").getValues());
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

    @RequestMapping("/changeStage.do")
    @ResponseBody
    public Map<String,Object> changeStage(String id,String stage){
        User user = (User) session.getAttribute("user");
        String editBy = user.getName();
        transactionService.changeStage(id,stage,editBy);
        return new HashMap<String, Object>(){{
            put("success",true);
        }};
    }
}
