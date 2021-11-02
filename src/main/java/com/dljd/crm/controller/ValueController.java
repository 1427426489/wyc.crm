package com.dljd.crm.controller;

import com.dljd.crm.beans.Value;
import com.dljd.crm.services.TypeService;
import com.dljd.crm.services.ValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/value")
public class ValueController {

    @Autowired
    private ValueService valueService;

    @Autowired
    private TypeService typeService;

    @RequestMapping("/indexView")
    public String indexView(Model model){
        model.addAttribute("vList",valueService.getAll());
        return "settings/dictionary/value/index";
    }

    @RequestMapping("/saveView")
    public String saveView(Model model){
        model.addAttribute("tList",typeService.getAll());
        return "settings/dictionary/value/save";
    }

    @RequestMapping("/save.do")
    public String saveDo(Value value){
        valueService.add(value);
        return "redirect:/value/indexView";
    }

    @RequestMapping("/delete.do")
    public String deleteDo(String[] ids){
        valueService.delete(ids);
        return "redirect:/value/indexView";
    }

    @RequestMapping("/editView")
    public String editView(String id,Model model){
        model.addAttribute("tList",typeService.getAll());
        model.addAttribute("value",valueService.get(id));
        return "settings/dictionary/value/edit";
    }

    @RequestMapping("/update.do")
    public String updateDo(Value value){
        valueService.update(value);
        return "redirect:/value/indexView";
    }
}
