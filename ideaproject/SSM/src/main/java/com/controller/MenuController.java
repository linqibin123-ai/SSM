package com.controller;

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

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping(value = "/menuController")
public class MenuController {
    @Autowired
    private Menu menu;
    @Autowired
    private HttpSession session;
    @Autowired
    private MenuService menuService;

    @RequestMapping("/menuList")
    @ResponseBody
    public List<Map<String,Object>> menuList(){
        List<Menu> menuList=menuService.menuList();
        Map<String,Object> treeNode =null;
        Map<String,Map<String,Object>> nodeMap = new LinkedHashMap<>();
        List<Map<String,Object>> mapList=new ArrayList<>();
        for (Menu menu:menuList) {
            treeNode = new LinkedHashMap<String,Object>();
            String id = menu.getId();
            String name = menu.getName();
            String parenid = menu.getParentid();
            treeNode.put("id",id);
            treeNode.put("text",name);
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

    @RequestMapping("/menuListByUserId")
    @ResponseBody
    public List<Map<String,Object>> menuListByUserId(){
        User user=(User) session.getAttribute("user");
        List<Menu> menuList=menuService.menuListByUserid(user.getId());
        Map<String,Object> treeNode =null;
        Map<String,Map<String,Object>> nodeMap = new LinkedHashMap<>();
        List<Map<String,Object>> mapList=new ArrayList<>();
        for (Menu menu:menuList) {
            treeNode = new LinkedHashMap<String,Object>();
            String id = menu.getId();
            String name = menu.getName();
            String parenid = menu.getParentid();
            treeNode.put("id",id);
            treeNode.put("text",name);
            nodeMap.put(String.valueOf(id),treeNode);
            if(menu.getParentid().equals("0")){
                mapList.add(treeNode);
            }else {
                Map<String,Object> parentNode=nodeMap.get(String.valueOf(parenid));
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
