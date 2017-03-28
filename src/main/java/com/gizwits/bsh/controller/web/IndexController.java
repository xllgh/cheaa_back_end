package com.gizwits.bsh.controller.web;

import com.gizwits.bsh.common.bean.BaseRespVO;
import com.gizwits.bsh.enums.ErrType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhl on 2016/12/23.
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/wiki",method = RequestMethod.GET)
    public void gotoWiki(HttpServletResponse response){
        try{
            response.sendRedirect("/wiki/index.html");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/indexSkip",method = RequestMethod.GET)
    public String indexSkip(){
        return "index_skip";
    }

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String home(Model model){
        return "home";
    }

    /**
     * 查询当前登录的用户ID
     * @return
     */
    @RequestMapping(value = "/whoami")
    @ResponseBody
    public BaseRespVO whoami() {
        BaseRespVO result = new BaseRespVO(ErrType.SUCCESS);
        result.setMsg(null);
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            Session session = subject.getSession();
            if (session  != null) {
                Object userid = session.getAttribute("userid");
                if (userid != null) {
                    result.setMsg(userid.toString());
                }
            }
        }
        return result;
    }
}
