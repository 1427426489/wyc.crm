package com.dljd.crm.controller;

import com.dljd.crm.beans.Contacts;
import com.dljd.crm.beans.Type;
import com.dljd.crm.beans.User;
import com.dljd.crm.services.ContactsService;
import com.dljd.crm.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactsService contactsService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/indexView")
    public String indexView(Model model){
        Type source = typeService.get("source");
        model.addAttribute("sourceList",source.getValues());
        Type application = typeService.get("application");
        model.addAttribute("appellationList",application.getValues());
        return "workbench/contacts/index";
    }

    @RequestMapping("/getCustomerName.json")
    @ResponseBody
    public List<String> getCustomerName(String name){
        return contactsService.getCustomerName(name);
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Object saveDo(Contacts contacts){
        //客户名称(唯一约束)作为customerId字段的值
        User user = (User) request.getSession().getAttribute("user");
        contacts.setCreateBy(user.getName());
        contactsService.add(contacts);
        return new HashMap<String,Object>(){{
            put("success",true);
        }};
    }
}
