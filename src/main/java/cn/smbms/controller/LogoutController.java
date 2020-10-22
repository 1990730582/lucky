package cn.smbms.controller;


import cn.smbms.tools.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {


    @RequestMapping("/login")
    public String toLogin(){

        return "login";
    }


}
