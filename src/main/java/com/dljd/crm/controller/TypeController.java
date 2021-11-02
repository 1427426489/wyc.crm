package com.dljd.crm.controller;

import com.dljd.crm.beans.Type;
import com.dljd.crm.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @RequestMapping("/indexView")
    public String indexView(Model model){
        model.addAttribute("tList",typeService.getAll());
        return "settings/dictionary/type/index";
    }

    @RequestMapping("/saveView")
    public String saveView(){
        return "settings/dictionary/type/save";
    }

    @RequestMapping("/checkCode.do")
    @ResponseBody
    public boolean checkCodeDo(String code){
        return typeService.getCheckCode(code);
    }

    @RequestMapping("/save.do")
    public String saveDo(Type type){
        typeService.add(type);
        return "redirect:/type/indexView";
    }

    @RequestMapping("/delete.do")
    public String deleteDo(String[] ids){
        typeService.delete(ids);
        return "redirect:/type/indexView";
    }

    @RequestMapping("/editView")
    public String editView(String code,Model model){
        model.addAttribute("type",typeService.get(code));
        return "settings/dictionary/type/edit";
    }

    @RequestMapping("/edit.do")
    public String editDo(Type type){
        typeService.update(type);
        return "redirect:/type/indexView";
    }
}
