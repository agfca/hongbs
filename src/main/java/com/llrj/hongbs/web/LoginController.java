package com.llrj.hongbs.web;

import com.llrj.hongbs.domain.User;
import com.llrj.hongbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    /**
     * 根据session
     * 返回用户首页或登录页
     * */
    @RequestMapping("/")
    public String homePage(HttpSession session) {
        User user = (User)session.getAttribute("user");
        if(user!=null){
            return getIndex(user);
        }else{
            return "login";
        }
    }

    /**
     * 根据session
     * 返回用户首页
     * */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(HttpSession session) {
        User user = (User)session.getAttribute("user");
        if(user!=null){
            return getIndex(user);
        }else{
            return "login";
        }
    }

    /**
     * 直接url访问登录页面
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView getLogin() {
    	ModelAndView mvn = new ModelAndView("login");
        return mvn;
    }

    /**
     * 验证登录
     * 判断用户名密码是否正确，正确添加session
     * */
    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestParam String username,@RequestParam String pwd,HttpSession session) {
        String msg = "";
        if(!StringUtils.isEmptyOrWhitespace(username)&&!StringUtils.isEmptyOrWhitespace(pwd)){
            User user = userService.login(username,pwd);
            if(user!=null){
                //HttpSession session = request.getSession();
                //user.setPwd(null);//session 不存入pwd
                session.setAttribute("user",user);
            }else{
                msg = "用户名或密码错误";
            }
        }else{
            msg = "请输入用户名和密码";
        }
        return msg;
    }

    /**
     * 根据user 返回相应页面  TODO
     */
    public String getIndex(User user){
        /*判断user level*/
        if("9_1".equals(user.getLevel())) {//cang9_1
            return "chenyi";
        }else if("9_2".equals(user.getLevel())) {//cang9_2
            return "lifu";
        }else if("9_3".equals(user.getLevel())) {//cang9_3
            return "yundongfu";
        }else{
            if(("0").equals(user.getLevel())){//admin
                return "admin";
            }else if(("1").equals(user.getLevel())){//销售人员
                return "xiaoshou";
            }else if(("2_5").equals(user.getLevel())){//工艺部
                return "gongyi";
            }else if(("3").equals(user.getLevel())){//下单
                return "xiadan";
            }else if(("4").equals(user.getLevel())) {//制版
                return "zhiban";
            }else if(("5").equals(user.getLevel())) {//工艺
                return "gongyi";
            }else if(("6").equals(user.getLevel())) {//采购
                return "caigou";
            }else{//生成安排
                return "shengcan";
            }
        }
    }

    /**
     * 登出
     */
    @RequestMapping(value = "/layout")
    public String exit(HttpSession session) {
        session.removeAttribute("user");
        return "login";
    }
    
    
}
