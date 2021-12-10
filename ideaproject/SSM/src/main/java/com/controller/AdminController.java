package com.controller;

import com.bean.Admin;
import com.bean.Menu;
import com.bean.User;
import com.service.AdminService;
import com.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/adminController")
public class AdminController {
    @Autowired
    private Admin admin;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AdminService adminService;
    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/returnAdminLogin",method = RequestMethod.GET)
    public ModelAndView returnAdminLogin(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("adminlogin");
        return mv;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView login(Admin admin, HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        Admin admin1=adminService.login(admin);
        if (admin1==null) {
            mv.addObject("id",admin.getId());
            mv.addObject("loginerrorMessage","账号或密码错误");
            mv.setViewName("adminlogin");
        } else {
            session.setAttribute("admin",admin);
            session.setAttribute("logintype","admin");
            mv.setViewName("loginsuccess");
        }
        return mv;
    }

    @RequestMapping(value ="/menuList",method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String,Object>> menuList(){
        String menuid = request.getParameter("1");
        List<Menu> menuList=menuService.menuList();
        List<Menu> finalMenuList=new ArrayList<>();
        for (Menu m:menuList) {
            String r_id = m.getId();
            if(r_id.startsWith(menuid)) {
                finalMenuList.add(m);
            }
        }
        Map<String,Object> treeNode =null;
        Map<String,Map<String,Object>> nodeMap = new LinkedHashMap<>();
        List<Map<String,Object>> mapList=new ArrayList<>();
        for (Menu menu:finalMenuList) {
            treeNode = new LinkedHashMap<String,Object>();
            String id = menu.getId();
            String name = menu.getName();
            String parenid = menu.getParentid();
            String url = menu.getUrl();
            int isleaf = menu.getIsleaf();
            treeNode.put("id",id);
            treeNode.put("text",name);
            if(isleaf==1)
                treeNode.put("url",request.getContextPath()+url);
            treeNode.put("isleaf",isleaf);
            nodeMap.put(id,treeNode);
            if(menu.getParentid().equals("0")){
                mapList.add(treeNode);
            }else {
                Map<String,Object> parentNode=nodeMap.get(parenid);
                List<Map<String,Object>> children = null;
                if (parentNode.get("children")==null){
                    children = new ArrayList<>();
                }else {
                    children = (List<Map<String,Object>>)parentNode.get("children");
                }
                children.add(treeNode);
                parentNode.put("children",children);
            }
        }
        return mapList;
    }
}
