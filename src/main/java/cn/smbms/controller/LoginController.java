package cn.smbms.controller;

import cn.smbms.pojo.User;
import cn.smbms.service.user.UserService;
import cn.smbms.service.user.UserServiceImpl;
import cn.smbms.tools.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
public class LoginController {
    @Resource
    UserService userService ;

    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    public String login(User user, String error, Model model) {
        System.out.println("login ============ ");
        //获取用户名和密码
        String userCode = user.getUserCode();
        String userPassword = user.getUserPassword();
        //调用service方法，进行用户匹配

        User user1 = userService.login(userCode, userPassword);
        if (null != user1) {//登录成功
            //放入session
            model.addAttribute(Constants.USER_SESSION, user1);
            //页面跳转（frame.jsp）
            return "frame";
        } else {
            //页面跳转（login.jsp）带出提示信息--转发
            error = "用户名或密码不正确";
            model.addAttribute("error", error);
            return "login";
        }
    }
}
