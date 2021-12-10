package com.controller;

import com.assist.StringUtil;
import com.bean.Department;
import com.bean.Role;
import com.bean.User;
import com.dao.RoleMapper;
import com.github.pagehelper.PageHelper;
import com.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/roleController")
public class RoleController {
    @Autowired
    private Role role;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/roleList")
    public ModelAndView roleList(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("rolelist");
        return mv;
    }

    @RequestMapping(value = "/getRoleList")
    @ResponseBody
    public Map<String,Object> getRoleList() throws Exception{
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String roleid = request.getParameter("roleid");
        String name = request.getParameter("name");
        if (StringUtil.isEmpty(sort))
            sort="roleid";
        if(StringUtil.isEmpty(order))
            order="asc";
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if (StringUtil.isEmpty(page))
            sort="1";
        if(StringUtil.isEmpty(rows))
            order="10";
        Map<String,Object> map = new HashMap<>();
        map.put("sort",sort);
        map.put("order",order);
        map.put("roleid",roleid);
        map.put("name",name);
        int int_page = Integer.parseInt(page);
        int int_rows = Integer.parseInt(rows);
        int totalCount = roleService.getTotalCount(map);
        PageHelper.startPage(int_page,int_rows);
        List<Role> roleList = roleService.getRoleList(map);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("total", totalCount);
        dataMap.put("rows", roleList);
        return dataMap;
    }

    @RequestMapping("/returnEditRole")
    public  ModelAndView returnEditRole(){
        String roleid = request.getParameter("roleid");
        Role role=roleService.selectById(roleid);
        ModelAndView mv = new ModelAndView();
        mv.addObject("role",role);
        mv.setViewName("editrole");
        return mv;
    }

    @RequestMapping("/editRole")
    @ResponseBody
    public Map<String,Object> editRole(Role role){
        int i=roleService.updateRole(role);
        Map<String,Object> map = new HashMap<>();
        boolean flag = true;
        if (i!=1) {
            flag = false;
        }
        map.put("flag",flag);
        return map;
    }

    @RequestMapping("/returnAddRole")
    public ModelAndView returnAddRole(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("addrole");
        return mv;
    }

    @RequestMapping("/addRole")
    @ResponseBody
    public Map<String,Object> addRole(Role role){
        System.out.println(role);
        int i=roleService.insertRole(role);
        Map<String,Object> map = new HashMap<>();
        boolean flag = true;
        if (i!=1) {
            flag = false;
        }
        map.put("flag",flag);
        return map;
    }

    @RequestMapping("/deleteRole")
    @ResponseBody
    public Map<String,Object> deleteRole(String roleid){
        int i=roleService.delete(roleid);
        Map<String,Object> map = new HashMap<>();
        boolean flag = true;
        if (i!=1)
            flag = false;
        map.put("flag",flag);
        return map;
    }
}
