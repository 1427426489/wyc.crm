package com.dljd.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/err")
public class ErrorController {

    @RequestMapping("/indexView")
    public String indexView(){
        return "err";
    }
}
