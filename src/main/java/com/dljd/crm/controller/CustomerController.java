package com.dljd.crm.controller;

import com.dljd.crm.beans.Customer;
import com.dljd.crm.beans.CustomerRemark;
import com.dljd.crm.beans.Page;
import com.dljd.crm.beans.User;
import com.dljd.crm.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/indexView")
    public String indexView(){
        return "workbench/customer/index";
    }

    @RequestMapping("/getPage.json")
    @ResponseBody
    public Object getPage(Page page){
        return customerService.getSome(page);
    }

    @RequestMapping("/getExists.json")
    @ResponseBody
    public boolean getExists(String name){
        return customerService.getExists(name);
    }

    @RequestMapping("/save.do")
    @ResponseBody
    public Object saveDo(Customer customer){
        User user = (User) session.getAttribute("user");
        customer.setCreateBy(user.getName());
        customerService.add(customer);
        return new HashMap<String,Object>(){{
            put("success",true);
        }};
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(String[] ids){
        customerService.delete(ids);
        return new HashMap<String,Object>(){{
            put("success",true);
        }};
    }

    @RequestMapping("/get.json")
    @ResponseBody
    public Customer get(String id){
        return customerService.get(id);
    }

    @RequestMapping("/edit.do")
    @ResponseBody
    public Object edit(Customer customer){
        User user = (User) session.getAttribute("user");
        customer.setEditBy(user.getName());
        customerService.update(customer);
        return new HashMap<String,Object>(){{
            put("success",true);
        }};
    }

    @RequestMapping("/detailView")
    public String detailView(String id){
        request.setAttribute("id",id);
        return "forward:/WEB-INF/jsp/workbench/customer/detail.jsp";
    }

    @RequestMapping("/getRemarks.json")
    @ResponseBody
    public List<CustomerRemark> getRemarks(String id){
        return customerService.getRemarks(id);
    }
}
