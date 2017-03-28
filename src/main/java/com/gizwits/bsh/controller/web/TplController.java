package com.gizwits.bsh.controller.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Create by zhl 12/23 2016
 */
@Controller
@RequestMapping(value = "/tpl")
public class TplController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    /**
    * 加载头部模板
    */
    @RequestMapping(value = "/header",method = RequestMethod.GET)
    public String header(Model model){
        Subject subject = SecurityUtils.getSubject();
        if (subject == null) return "redirect:login";
        Session shiroSession = subject.getSession();
        if (shiroSession == null) return "redirect:login";
        Object useridObj = shiroSession.getAttribute("userid");
        if (useridObj == null) return "redirect:login";
        String userid = useridObj.toString();
//        Long userid = Long.valueOf(shiroSession.getAttribute("userid").toString());
//        Integer userType = (Integer)shiroSession.getAttribute("userType");
        String username = (String)shiroSession.getAttribute("username");
        model.addAttribute("userid", userid);
        model.addAttribute("username",username);
        return "tpl/header";
    }

    /**
    * 加载底部模板
    */
    @RequestMapping(value = "/footer",method = RequestMethod.GET)
    public String footer(){
        return "tpl/footer";
    }

    /**
    * 加载菜单模板
    * */
    @RequestMapping(value = "/sidebar",method = RequestMethod.GET)
    public String sidebar(){
        return "tpl/sidebar";
    }

    /**
    * 加载快速菜单
    */
    @RequestMapping(value = "/quick_sidebar",method = RequestMethod.GET)
    public String quickSidebar(){
        return "tpl/quick-sidebar";
    }

    /**
    * 加载主题模板
    */
    @RequestMapping(value = "/theme_panel",method = RequestMethod.GET)
    public String themePanel(){
        return "tpl/theme-panel";
    }
}
