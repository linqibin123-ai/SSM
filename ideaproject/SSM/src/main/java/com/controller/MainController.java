package com.controller;

import com.bean.Menu;
import com.bean.User;
import com.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/mainController")
public class MainController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/returnMain",method = RequestMethod.GET)
    public ModelAndView returnAdminLogin(){
        ModelAndView mv = new ModelAndView();
        List<Menu> menuList = null;
        if (session.getAttribute("logintype")=="admin")
            menuList=menuService.menuList();
        else {
            User user = (User) session.getAttribute("user");
            menuList = menuService.menuListByUserid(user.getId());
        }
        List<Menu> bigMenu=new ArrayList<>();
        for (Menu m:menuList) {
            String parentid = m.getParentid();
            if(parentid.equals("0")){
                bigMenu.add(m);
            }
        }
        mv.addObject("bigMenu",bigMenu);
        mv.setViewName("main");
        return mv;
    }
}
